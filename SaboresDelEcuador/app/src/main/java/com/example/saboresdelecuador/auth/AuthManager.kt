package com.example.saboresdelecuador.auth

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object AuthManager {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    /**
     * Registra un nuevo usuario en Firebase Authentication y almacena datos en Firestore.
     */
    fun registerUser(context: Context, nickname: String, email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (nickname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            onFailure("Todos los campos son obligatorios.")
            return
        }

        // Registrar en Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                    // Guardar información adicional en Firestore
                    val user = hashMapOf(
                        "nickname" to nickname,
                        "email" to email,
                        "fecha_registro" to com.google.firebase.Timestamp.now()
                    )

                    db.collection("Usuarios").document(userId)
                        .set(user)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            onFailure("Error al guardar datos: ${e.message}")
                        }
                } else {
                    onFailure(task.exception?.message ?: "Error en el registro")
                }
            }
    }

    /**
     * Inicia sesión con Firebase Authentication.
     */
    fun loginUser(
        context: Context, email: String, password: String,
        onSuccess: () -> Unit, onFailure: (String) -> Unit
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            onFailure("Ingrese usuario y contraseña.")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure("Error al iniciar sesión: ${e.message}")
            }
    }

    /**
     * Envía un correo para recuperar la contraseña.
     */
    fun recoverPassword(context: Context, email: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (email.isEmpty()) {
            onFailure("Ingrese su correo electrónico.")
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                Toast.makeText(context, "Correo de recuperación enviado", Toast.LENGTH_SHORT).show()
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure("Error: ${e.message}")
            }
    }
}
