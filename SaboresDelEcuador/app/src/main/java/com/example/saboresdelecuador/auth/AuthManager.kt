package com.example.saboresdelecuador.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

object AuthManager {
    private val db = FirebaseFirestore.getInstance()

    /**
     * Registra un nuevo usuario en Firestore con usuario y contraseña.
     */
    fun registerUser(
        context: Context,
        nickname: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (nickname.isEmpty() || password.isEmpty()) {
            onFailure("Todos los campos son obligatorios.")
            return
        }

        val user = hashMapOf(
            "nickname" to nickname,
            "password" to password,  // ⚠️ No se recomienda almacenar contraseñas sin encriptar
            "fecha_registro" to Timestamp.now()
        )

        db.collection("Usuarios")
            .document(nickname)  // 🔹 Guardar usuario con el nickname como ID único
            .set(user)
            .addOnSuccessListener {
                Log.d("REGISTER_FIRESTORE", "✅ Usuario registrado correctamente")
                Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("REGISTER_FIRESTORE", "⚠️ Error al registrar: ${e.message}")
                onFailure("Error al registrar: ${e.message}")
            }
    }

    /**
     * Inicia sesión verificando en Firestore con nickname y contraseña.
     */
    fun loginUser(
        context: Context,
        nickname: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (nickname.isEmpty() || password.isEmpty()) {
            onFailure("Ingrese usuario y contraseña.")
            return
        }

        Log.d("LOGIN_FIRESTORE", "Buscando usuario: $nickname en Firestore")

        db.collection("Usuarios")
            .whereEqualTo("nickname", nickname) // 🔹 Busca por nickname
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userDoc = documents.documents[0]
                    val storedPassword = userDoc.getString("password") ?: ""

                    Log.d("LOGIN_FIRESTORE", "Usuario encontrado: ${userDoc.getString("nickname")}")
                    Log.d("LOGIN_FIRESTORE", "Contraseña en Firestore: $storedPassword")
                    Log.d("LOGIN_FIRESTORE", "Contraseña ingresada: $password")

                    if (storedPassword == password) {
                        Log.d("LOGIN_FIRESTORE", "✅ Inicio de sesión exitoso")
                        onSuccess()
                    } else {
                        Log.d("LOGIN_FIRESTORE", "❌ Contraseña incorrecta")
                        onFailure("Usuario o contraseña incorrectos.")
                    }
                } else {
                    Log.d("LOGIN_FIRESTORE", "❌ Usuario no encontrado en Firestore")
                    onFailure("Usuario o contraseña incorrectos.")
                }
            }
            .addOnFailureListener { e ->
                Log.e("LOGIN_FIRESTORE", "⚠️ Error al acceder a Firestore: ${e.message}")
                onFailure("Error al acceder a Firestore.")
            }
    }

    fun recoverPassword(
        context: Context,
        username: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (username.isEmpty()) {
            onFailure("Ingrese su nombre de usuario.")
            return
        }

        Log.d("RECOVER_PASSWORD", "Buscando en Firestore el usuario: $username")

        db.collection("Usuarios")
            .whereEqualTo("nickname", username) // 🔍 Busca por el nombre de usuario
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userDoc = documents.documents[0]
                    val storedPassword = userDoc.getString("password") ?: "No disponible"

                    Log.d("RECOVER_PASSWORD", "Contraseña recuperada: $storedPassword")

                    // Simulamos un mensaje de recuperación de contraseña
                    val mensaje = "Tu contraseña es: $storedPassword"
                    onSuccess(mensaje)
                } else {
                    Log.d("RECOVER_PASSWORD", "Usuario no encontrado")
                    onFailure("No se encontró el usuario.")
                }
            }
            .addOnFailureListener { e ->
                Log.e("RECOVER_PASSWORD", "Error al buscar usuario: ${e.message}")
                onFailure("Error al recuperar la contraseña.")
            }
    }
}
