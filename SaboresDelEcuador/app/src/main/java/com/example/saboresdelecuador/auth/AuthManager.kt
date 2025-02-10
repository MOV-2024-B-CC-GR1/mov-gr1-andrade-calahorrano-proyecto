package com.example.saboresdelecuador.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.saboresdelecuador.home.HomeActivity
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
            .whereEqualTo("nickname", nickname)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userDoc = documents.documents[0]
                    val storedPassword = userDoc.getString("password") ?: ""

                    if (storedPassword == password) {
                        // ✅ Guardar usuario en SharedPreferences
                        saveUserNicknameToLocal(context, nickname)

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

    /**
     * Recupera la contraseña del usuario en Firestore.
     */
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

    // 🔹 NUEVAS FUNCIONES AÑADIDAS

    /**
     * Actualiza el nickname del usuario en Firestore.
     */
    fun updateUserNicknameFirestore(
        oldNickname: String,
        newNickname: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (newNickname.isEmpty()) {
            onFailure("El nuevo nombre no puede estar vacío.")
            return
        }

        val userRef = db.collection("Usuarios").document(oldNickname)

        userRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val userData = document.data
                if (userData != null) {
                    db.collection("Usuarios").document(newNickname).set(userData)
                        .addOnSuccessListener {
                            userRef.delete() // Borrar el usuario con el nombre anterior
                            Log.d("UPDATE_NICKNAME", "✅ Nickname actualizado correctamente")
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            Log.e(
                                "UPDATE_NICKNAME",
                                "⚠️ Error al actualizar nickname: ${e.message}"
                            )
                            onFailure("Error al actualizar nickname: ${e.message}")
                        }
                }
            } else {
                onFailure("El usuario no existe.")
            }
        }.addOnFailureListener { e ->
            onFailure("Error al obtener usuario: ${e.message}")
        }
    }

    /**
     * Guarda el nickname del usuario en SharedPreferences.
     */
    fun saveUserNicknameToLocal(context: Context, nickname: String) {
        val sharedPreferences = context.getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("nickname", nickname)
        editor.apply()
        Log.d("SHARED_PREFS", "✅ Nickname guardado en SharedPreferences: $nickname")
    }

    /**
     * Obtiene el nickname del usuario almacenado en SharedPreferences.
     */
    fun getSavedUserNickname(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
        return sharedPreferences.getString("nickname", null)
    }

    /**
     * Carga los datos del usuario desde Firestore.
     */
    fun loadUserDataFirestore(
        context: Context,
        onSuccess: (Map<String, Any>?) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val nickname = getSavedUserNickname(context)

        if (nickname.isNullOrEmpty()) {
            onFailure("No se encontró el usuario en sesión.")
            return
        }

        db.collection("Usuarios").document(nickname)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    onSuccess(document.data)
                } else {
                    onFailure("Usuario no encontrado en Firestore.")
                }
            }
            .addOnFailureListener { e ->
                onFailure("Error al obtener datos del usuario: ${e.message}")
            }
    }

    /**
     * Actualiza el nickname del usuario en Firestore sin eliminar el usuario.
     */
    fun updateUserNicknameInFirestore(
        context: Context,
        oldNickname: String,
        newNickname: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (newNickname.isEmpty()) {
            onFailure("El nuevo nombre no puede estar vacío.")
            return
        }

        // 🔹 Buscar el usuario por su nickname original
        db.collection("Usuarios").whereEqualTo("nickname", oldNickname).get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userDoc = documents.documents[0] // Obtener el primer documento
                    val userId = userDoc.id // ID del documento en Firestore

                    // 🔹 Actualizar solo el campo "nickname", no el ID
                    db.collection("Usuarios").document(userId)
                        .update("nickname", newNickname)
                        .addOnSuccessListener {
                            // ✅ Guardar el nuevo nickname en SharedPreferences
                            saveUserNicknameToLocal(context, newNickname)
                            Log.d("UPDATE_NICKNAME", "✅ Nickname actualizado correctamente")
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            Log.e("UPDATE_NICKNAME", "⚠️ Error al actualizar nickname: ${e.message}")
                            onFailure("Error al actualizar nickname: ${e.message}")
                        }
                } else {
                    onFailure("Usuario no encontrado en Firestore.")
                }
            }
            .addOnFailureListener { e ->
                onFailure("Error al buscar usuario: ${e.message}")
            }
    }

    /**
     * Cierra sesión del usuario y limpia `SharedPreferences`.
     */
    fun logoutUser(context: Context) {
        val sharedPreferences = context.getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        Log.d("LOGOUT_FIRESTORE", "✅ Usuario desconectado y nickname eliminado")

        val intent = Intent(context, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    /**
     * Actualiza la contraseña del usuario en Firestore.
     */
    fun updateUserPasswordFirestore(
        nickname: String,
        newPassword: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (newPassword.isEmpty()) {
            onFailure("La nueva contraseña no puede estar vacía.")
            return
        }

        db.collection("Usuarios").document(nickname)
            .update("password", newPassword)
            .addOnSuccessListener {
                Log.d("UPDATE_PASSWORD", "✅ Contraseña actualizada correctamente")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("UPDATE_PASSWORD", "⚠️ Error al actualizar contraseña: ${e.message}")
                onFailure("Error al actualizar contraseña: ${e.message}")
            }
    }

    /**
     * Actualiza la contraseña del usuario en Firestore sin eliminar el usuario.
     */
    fun updateUserPasswordInFirestore(
        nickname: String,
        newPassword: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (newPassword.isEmpty()) {
            onFailure("La nueva contraseña no puede estar vacía.")
            return
        }

        // 🔹 Buscar el usuario por su nickname
        db.collection("Usuarios").whereEqualTo("nickname", nickname).get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userDoc = documents.documents[0] // Obtener el primer documento
                    val userId = userDoc.id // ID del documento en Firestore

                    // 🔹 Actualizar solo el campo "password", sin cambiar el ID del documento
                    db.collection("Usuarios").document(userId)
                        .update("password", newPassword)
                        .addOnSuccessListener {
                            Log.d("UPDATE_PASSWORD", "✅ Contraseña actualizada correctamente en Firestore")
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            Log.e("UPDATE_PASSWORD", "⚠️ Error al actualizar contraseña: ${e.message}")
                            onFailure("Error al actualizar contraseña: ${e.message}")
                        }
                } else {
                    onFailure("Usuario no encontrado en Firestore.")
                }
            }
            .addOnFailureListener { e ->
                onFailure("Error al buscar usuario: ${e.message}")
            }
    }
}
