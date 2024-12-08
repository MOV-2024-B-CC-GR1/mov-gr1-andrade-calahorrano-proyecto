

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
Continuamos con las siguientes **épicas** y **historias de usuario**:

---

#### **GR001-03: Actualizar una receta agregando fotos o pasos personalizados**  
**Título:** Personalización de recetas  
**Estimación:** 3 horas  
**Prioridad:** Media  

**Historia de usuario:**  
Como usuario de la aplicación,  
quiero actualizar una receta agregando fotos o pasos personalizados,  
para darle un toque especial y adaptarlo a mis necesidades.

**Criterios de aceptación:**  
- El sistema debe permitir al usuario agregar fotos de la receta.  
- El sistema debe permitir al usuario agregar, eliminar o reordenar pasos en la receta.
- Al guardar los cambios, la receta se actualizará en la base de datos y se reflejarán las modificaciones inmediatamente.

**Escenario:** Actualización de receta  
Dado que un usuario desea personalizar una receta,  
Cuando agrega o edita fotos y pasos,  
Entonces el sistema guarda los cambios y muestra un mensaje de confirmación.

**Tasks:**  
| Tarea                                          | Tiempo Estimado |  
|-----------------------------------------------|-----------------|  
| T1: Crear la interfaz para agregar fotos y pasos personalizados. | 60 min |  
| T2: Implementar lógica para guardar las fotos y pasos en la base de datos. | 70 min |  
| T3: Mostrar mensaje de confirmación al guardar cambios. | 30 min |

**Lista de revisión:**  
- [x] Independiente  
- [x] Negociable  
- [x] Valioso  
- [x] Estimable  
- [x] Pequeño  

---

#### **GR001-04: Editar completamente una receta, incluyendo su región asignada**  
**Título:** Edición completa de receta  
**Estimación:** 3 horas  
**Prioridad:** Media  

**Historia de usuario:**  
Como usuario de la aplicación,  
quiero poder editar completamente una receta,  
incluyendo cambiar su región asignada,  
para ajustar la receta a mis preferencias o necesidades.

**Criterios de aceptación:**  
- El sistema debe permitir al usuario editar todos los detalles de la receta: nombre, ingredientes, pasos, fotos y región.
- Los cambios se guardarán en la base de datos y se reflejarán de inmediato en la pantalla de la receta.

**Escenario:** Edición de receta  
Dado que un usuario desea cambiar su receta,  
Cuando edita todos los detalles y confirma los cambios,  
Entonces el sistema actualiza la receta en la base de datos y muestra un mensaje de éxito.

**Tasks:**  
| Tarea                                          | Tiempo Estimado |  
|-----------------------------------------------|-----------------|  
| T1: Crear la interfaz de edición completa para la receta. | 60 min |  
| T2: Implementar lógica para actualizar los detalles en la base de datos. | 90 min |  
| T3: Mostrar mensaje de éxito al guardar los cambios. | 30 min |

**Lista de revisión:**  
- [x] Independiente  
- [x] Negociable  
- [x] Valioso  
- [x] Estimable  
- [x] Pequeño  

---

#### **GR001-05: Eliminar una receta creada por el usuario**  
**Título:** Eliminación de receta  
**Estimación:** 2 horas  
**Prioridad:** Alta  

**Historia de usuario:**  
Como usuario de la aplicación,  
quiero eliminar una receta creada por mí,  
para borrar contenido innecesario y evitar errores en la búsqueda.

**Criterios de aceptación:**  
- El sistema debe mostrar un mensaje de confirmación antes de eliminar.
- La receta será eliminada de la base de datos y no será accesible por el usuario.
- Se mostrará un mensaje de éxito al completar la eliminación.

**Escenario:** Eliminación de receta  
Dado que un usuario desea eliminar una receta,  
Cuando confirma la acción,  
Entonces el sistema elimina la receta de la base de datos y muestra un mensaje de éxito.

**Tasks:**  
| Tarea                                          | Tiempo Estimado |  
|-----------------------------------------------|-----------------|  
| T1: Crear pantalla de confirmación para eliminar receta. | 40 min |  
| T2: Implementar lógica para eliminar la receta de la base de datos. | 70 min |  
| T3: Mostrar mensaje de confirmación de éxito.             | 30 min |

**Lista de revisión:**  
- [x] Independiente  
- [x] Negociable  
- [x] Valioso  
- [x] Estimable  
- [x] Pequeño  

---

#### **GR001-06: Compartir una receta a través de un enlace o redes sociales**  
**Título:** Compartición de recetas  
**Estimación:** 2 horas  
**Prioridad:** Media  

**Historia de usuario:**  
Como usuario de la aplicación,  
quiero compartir una receta a través de un enlace o redes sociales,  
para que otros usuarios puedan acceder a ella fácilmente.

**Criterios de aceptación:**  
- El sistema generará un enlace único para cada receta.
- El usuario podrá compartir la receta en redes sociales (Facebook, Twitter, WhatsApp, etc.)
- Al compartir, se abrirá la aplicación correspondiente al enlace generado.

**Escenario:** Compartición de receta  
Dado que un usuario desea compartir una receta,  
Cuando selecciona la opción de compartir,  
Entonces el sistema genera un enlace único y lo abre en las aplicaciones de redes sociales elegidas.

**Tasks:**  
| Tarea                                          | Tiempo Estimado |  
|-----------------------------------------------|-----------------|  
| T1: Crear la interfaz para compartir recetas.  | 40 min |  
| T2: Implementar lógica para generar un enlace único por receta. | 50 min |  
| T3: Abrir las aplicaciones de redes sociales correspondientes al seleccionar compartir. | 30 min |

**Lista de revisión:**  
- [x] Independiente  
- [x] Negociable  
- [x] Valioso  
- [x] Estimable  
- [x] Pequeño  

---

### **Épica: Búsqueda y Descubrimiento (BD001)**

---

#### **BD001-01: Buscar recetas por el nombre ingresado en el buscador**  
**Título:** Búsqueda por nombre  
**Estimación:** 2 horas  
**Prioridad:** Alta  

**Historia de usuario:**  
Como usuario de la aplicación,  
quiero buscar recetas por nombre,  
para encontrar de manera rápida y precisa las recetas que deseo.

**Criterios de aceptación:**  
- El sistema debe permitir la búsqueda por nombre y mostrar resultados ordenados.
- Los resultados deben mostrar la receta correspondiente con una vista previa de la imagen y los ingredientes.

**Escenario:** Búsqueda de recetas  
Dado que un usuario quiere buscar una receta específica,  
Cuando ingresa el nombre en el buscador,  
Entonces el sistema muestra los resultados relevantes.

**Tasks:**  
| Tarea                                          | Tiempo Estimado |  
|-----------------------------------------------|-----------------|  
| T1: Diseñar la interfaz de búsqueda.           | 30 min |  
| T2: Implementar lógica para búsqueda en la base de datos. | 60 min |  
| T3: Mostrar resultados de búsqueda con vista previa de recetas. | 30 min |

**Lista de revisión:**  
- [x] Independiente  
- [x] Negociable  
- [x] Valioso  
- [x] Estimable  
- [x] Pequeño  

---

#### **BD001-02: Filtrar recetas por región para encontrar opciones específicas**  
**Título:** Filtro por región  
**Estimación:** 2 horas  
**Prioridad:** Alta  

**Historia de usuario:**  
Como usuario de la aplicación,  
quiero filtrar recetas por región,  
para encontrar opciones específicas de Costa, Sierra o Oriente.

**Criterios de aceptación:**  
- El sistema debe permitir seleccionar una región para filtrar las recetas.
- Los resultados deben mostrar las recetas correspondientes con sus detalles principales.

**Escenario:** Filtrado por región  
Dado que un usuario desea buscar recetas en una región específica,  
Cuando selecciona la región deseada,  
Entonces el sistema muestra las recetas de esa región.

**Tasks:**  
| Tarea                                          | Tiempo Estimado |  
|-----------------------------------------------|-----------------|  
| T1: Crear la interfaz de selección de región. | 40 min |  
| T2: Implementar lógica de filtrado en la base de datos. | 60 min |  
| T3: Mostrar resultados filtrados.             | 20 min |

**Lista de revisión:**  
- [x] Independiente  
- [x] Negociable  
- [x] Valioso  
- [x] Estimable  
- [x] Pequeño  

---
