# AUTHORIZATION SERVER

Esta aplicación tiene como finalidad generar todo lo necesario para
el desarrollo con OAuth2 para el aplicativo que estamos haciendo para
el Hotel Pamplona. La idea es que estaremos conectando el tema de 
usuarios a este punto para el acceso a las credenciales así como los
accesos iniciales.

En este punto estaremos también trabajando con la conexión hacía Docker,
pero, la que hicimos en la API, por lo que solo traemos los elementos 
necesarios de perfil para la comunicación.

### Información de utilidad:
* Para la prueba de apertura del Login para generar el Authorization Code
hemos usado el sitio: ``https://oauthdebugger.com/``. Hemos proporcionado
los siguientes datos en los campos:
    * Authorize URI (required) = http://localhost:9776/authorization-server/oauth2/authorize
    * Redirect URI (required) = https://oauthdebugger.com/debug
    * Client ID (required) = client
    * Scope (required) = openid GET_ALL_CATEGORIES
    * State (autogenerated) = jft5wwudwd
    * Nonce (autogenerated) = 70on2e0mnqg
    * Response type (required) = code
    * Response mode (required) = form_post

Nos abrirá un link que se ve así: http://localhost:9776/authorization-server/login
La idea es que se nos generará un código que se ve así:
````dockerfile
FGvXMKaD83122Kzb6wA30-arU_zTXmksU3b5543zNFq-KzhIeITWfrcFOeyLuUGlgiG_537T4LzWLZsSN_jRzeKTER1liq77-_5cwmCH4pevI02KrICDDCGX424E1j
````