

### **Épica: Gestión de Usuario (GU001)**

| Código       | Historia de Usuario                                  | Prioridad | Estimación |
|--------------|------------------------------------------------------|-----------|------------|
| **GU001-01** | Registrarse ingresando un nickname.                  | Alta      | 2          |
| **GU001-02** | Actualizar el nickname del usuario.                  | Media     | 2          |
| **GU001-03** | Borrar el usuario y toda su información asociada.    | Alta      | 3          |

---

### **Épica: Gestión de Recetas (GR001)**

| Código       | Historia de Usuario                                                                  | Prioridad | Estimación |
|--------------|--------------------------------------------------------------------------------------|-----------|------------|
| **GR001-01** | Visualizar recetas filtradas por región (Costa, Sierra, Oriente).                    | Alta      | 3          |
| **GR001-02** | Buscar recetas por nombre o región.                                                  | Alta      | 2          |
| **GR001-03** | Actualizar una receta agregando fotos o pasos personalizados.                        | Media     | 3          |
| **GR001-04** | Editar completamente una receta, incluyendo su región asignada.                      | Media     | 3          |
| **GR001-05** | Eliminar una receta creada por el usuario.                                           | Alta      | 2          |
| **GR001-06** | Compartir una receta a través de un enlace o redes sociales.                         | Media     | 2          |

---

### **Épica: Búsqueda y Descubrimiento (BD001)**

| Código       | Historia de Usuario                                  | Prioridad | Estimación |
|--------------|------------------------------------------------------|-----------|------------|
| **BD001-01** | Buscar recetas por el nombre ingresado en el buscador.                             | Alta      | 2          |
| **BD001-02** | Filtrar recetas por región para encontrar opciones específicas.                     | Alta      | 2          |

---

### **Historias de Usuario**

---

### **Épica: Gestión de Usuario (GU001)**

---

#### **GU001-01: Registrarse ingresando un nickname**  
**Título:** Registro de usuario  
**Estimación:** 2 horas  
**Prioridad:** Alta  

**Historia de usuario:**  
Como usuario de la aplicación,  
quiero registrarme ingresando un nickname,  
para comenzar a usar la aplicación y acceder a las recetas disponibles.  

**Criterios de aceptación:**  
- El sistema debe permitir el ingreso de un nickname único.  
- El nickname se almacenará localmente en la base de datos.  
- Al completar el registro, el usuario será redirigido a la pantalla principal.  

**Escenario:** Registro de usuario  
Dado que un nuevo usuario desea registrarse,  
Cuando ingresa un nickname válido,  
Entonces el sistema guardará el nickname y lo llevará a la pantalla principal.  

**Tasks:**  
| Tarea                                          | Tiempo Estimado |  
|-----------------------------------------------|-----------------|  
| T1: Crear la interfaz para registro de usuario.             | 40 min |  
| T2: Implementar lógica para guardar el nickname.            | 50 min |  
| T3: Redirigir al usuario a la pantalla principal.           | 30 min |  

**Lista de revisión:**  
- [x] Independiente  
- [x] Negociable  
- [x] Valioso  
- [x] Estimable  
- [x] Pequeño  

---

#### **GU001-02: Actualizar el nickname del usuario**  
**Título:** Actualización del nickname  
**Estimación:** 2 horas  
**Prioridad:** Media  

**Historia de usuario:**  
Como usuario de la aplicación,  
quiero actualizar mi nickname,  
para reflejar cambios personales o corregir errores.  

**Criterios de aceptación:**  
- El sistema debe permitir editar y guardar un nuevo nickname.  
- El cambio se reflejará inmediatamente en la base de datos.  
- Se notificará al usuario con un mensaje de éxito.  

**Escenario:** Actualización del nickname  
Dado que un usuario autenticado desea cambiar su nickname,  
Cuando ingresa un nuevo nickname y lo confirma,  
Entonces el sistema actualizará la información en la base de datos.  

**Tasks:**  
| Tarea                                          | Tiempo Estimado |  
|-----------------------------------------------|-----------------|  
| T1: Crear una pantalla para edición del nickname. | 40 min |  
| T2: Implementar lógica para actualizar el nickname en la base de datos. | 40 min |  
| T3: Mostrar mensaje de confirmación de éxito.   | 20 min |  

**Lista de revisión:**  
- [x] Independiente  
- [x] Negociable  
- [x] Valioso  
- [x] Estimable  
- [x] Pequeño  

---

#### **GU001-03: Borrar el usuario y toda su información asociada**  
**Título:** Eliminación del usuario  
**Estimación:** 3 horas  
**Prioridad:** Alta  

**Historia de usuario:**  
Como usuario de la aplicación,  
quiero eliminar mi cuenta y todos mis datos,  
para garantizar mi privacidad y dejar de usar el servicio.  

**Criterios de aceptación:**  
- El sistema debe mostrar una confirmación antes de proceder.  
- Todos los datos asociados al usuario serán eliminados de forma local.  
- Se notificará al usuario con un mensaje de éxito al completar la operación.  

**Escenario:** Eliminación de usuario  
Dado que un usuario autenticado desea eliminar su cuenta,  
Cuando confirma la eliminación,  
Entonces el sistema borra su información local y muestra un mensaje de éxito.  

**Tasks:**  
| Tarea                                          | Tiempo Estimado |  
|-----------------------------------------------|-----------------|  
| T1: Crear pantalla de confirmación para eliminar usuario. | 40 min |  
| T2: Implementar lógica para eliminar datos asociados al usuario. | 90 min |  
| T3: Mostrar mensaje de confirmación de éxito.             | 30 min |  

**Lista de revisión:**  
- [x] Independiente  
- [x] Negociable  
- [x] Valioso  
- [x] Estimable  
- [x] Pequeño  

---

### **Épica: Gestión de Recetas (GR001)**

---

#### **GR001-01: Visualizar recetas filtradas por región**  
**Título:** Filtro de recetas por región  
**Estimación:** 3 horas  
**Prioridad:** Alta  

**Historia de usuario:**  
Como usuario de la aplicación,  
quiero filtrar las recetas por región (Costa, Sierra, Oriente),  
para encontrar opciones relacionadas con una ubicación específica.  

**Criterios de aceptación:**  
- El sistema debe permitir seleccionar una región.  
- Las recetas filtradas deben mostrar información básica como nombre, imagen y tiempo de preparación.  
- Al seleccionar una receta, se mostrará su detalle.  

**Escenario:** Filtrado por región  
Dado que un usuario desea buscar recetas,  
Cuando selecciona una región,  
Entonces el sistema muestra las recetas relacionadas con esa región.  

**Tasks:**  
| Tarea                                          | Tiempo Estimado |  
|-----------------------------------------------|-----------------|  
| T1: Crear la pantalla de selección de región. | 40 min |  
| T2: Implementar el filtrado en la base de datos. | 60 min |  
| T3: Diseñar la pantalla de resultados con recetas. | 60 min |  

**Lista de revisión:**  
- [x] Independiente  
- [x] Negociable  
- [x] Valioso  
- [x] Estimable  
- [x] Pequeño  

---

#### **GR001-02: Buscar recetas por nombre o región**  
**Título:** Búsqueda de recetas  
**Estimación:** 2 horas  
**Prioridad:** Alta  

**Historia de usuario:**  
Como usuario de la aplicación,  
quiero buscar recetas por nombre o región,  
para encontrar rápidamente lo que necesito.  

**Criterios de aceptación:**  
- El sistema debe permitir buscar por nombre o región.  
- Los resultados deben mostrarse de manera ordenada.  

**Escenario:** Búsqueda  
Dado que un usuario desea buscar recetas,  
Cuando ingresa un nombre o selecciona una región,  
Entonces el sistema muestra los resultados relevantes.  

**Tasks:**  
| Tarea                                          | Tiempo Estimado |  
|-----------------------------------------------|-----------------|  
| T1: Diseñar el buscador en la interfaz.        | 40 min |  
| T2: Implementar lógica de búsqueda en la base de datos. | 40 min |  
| T3: Mostrar los resultados de búsqueda.        | 40 min |  

**Lista de revisión:**  
- [x] Independiente  
- [x] Negociable  
- [x] Valioso  
- [x] Estimable  
- [x] Pequeño  

---
