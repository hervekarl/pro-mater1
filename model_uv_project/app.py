import streamlit as st
import pandas as pd
import joblib
import numpy as np

# Chargement du modèle et des encodeurs
model = joblib.load("modele_prediction_maladie.pkl")
scaler = joblib.load("scaler.pkl")
ohe = joblib.load("onehot_encoder.pkl")
mlb = joblib.load("symptom_encoder.pkl")

# Titre
st.title("🩺 Prédiction de Maladie à partir des Symptômes")

# Entrée utilisateur
age = st.slider("Âge", 1, 100, 30)
sexe = st.selectbox("Sexe", ["Homme", "Femme"])
fumeur = st.selectbox("Fumeur ?", ["Oui", "Non"])
tension = st.slider("Tension artérielle (cmHg)", 8.0, 20.0, 12.0)
temperature = st.slider("Température corporelle (°C)", 35.0, 42.0, 37.0)

# Symptômes observés
all_symptoms = mlb.classes_
symptomes = st.multiselect("Symptômes observés", all_symptoms)

# Bouton de prédiction
if st.button("🔍 Prédire la maladie"):
    if len(symptomes) < 3:
        st.warning("⚠️ Veuillez sélectionner **au moins trois symptômes** pour lancer la prédiction.")
    else:
        # Préparation des données
        input_num = scaler.transform([[age, tension, temperature]])
        input_cat = ohe.transform([[sexe, fumeur]])
        input_symptoms = mlb.transform([symptomes])
        input_final = np.concatenate([input_num, input_cat, input_symptoms], axis=1)

        # Prédiction + Probabilités
        probas = model.predict_proba(input_final)[0]
        classes = model.classes_

        prediction_index = np.argmax(probas)
        prediction = classes[prediction_index]
        prediction_proba = probas[prediction_index]

        # Résultat
        st.success(f"✅ Maladie prédite : **{prediction}**")
        st.info(f"📈 Probabilité de cette prédiction : **{prediction_proba*100:.2f}%**")

