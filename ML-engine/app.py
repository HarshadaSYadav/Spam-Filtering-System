from flask import Flask, request, jsonify
import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

app = Flask(__name__)

# Load dataset and preprocess
data = pd.read_csv("spam.csv", encoding='latin-1')
data = data.rename(columns={"v1": "label", "v2": "text"})
data = data[["label", "text"]]
data['label'] = data['label'].map({'ham': 0, 'spam': 1})

# Prepare training data
X = data['text']
y = data['label']
cv = CountVectorizer()
X = cv.fit_transform(X)
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

# Train model
model = MultinomialNB()
model.fit(X_train, y_train)

# Test accuracy
accuracy = accuracy_score(y_test, model.predict(X_test))
print(f"Model Accuracy: {accuracy:.2f}")

@app.route('/predict', methods=['POST'])
def predict():
    content = request.json.get('email')
    if not content:
        return jsonify({"error": "Email content is missing"}), 400
    
    data = cv.transform([content])
    prediction = model.predict(data)[0]
    return jsonify({"prediction": "spam" if prediction == 1 else "ham"})

if __name__ == '__main__':
    app.run(port=5000)
