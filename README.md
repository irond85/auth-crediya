# Authentication Service for CrediYa
 Api can authenticate users for CrediYa

# Modules
* Database -> MySql with R2DBC

# Features
* 1.0.0 -> implements register user in endpoint /api/v1/usuarios - POST
* 1.1.0 -> implements endpoint to obtain a user's email address using the user dni /api/v1/usuarios/dni/{dni} - GET
* 1.1.1 -> Fix UserUseCaseTest and build project
* 1.2.0 -> Implements endpoint to login user and obtain token