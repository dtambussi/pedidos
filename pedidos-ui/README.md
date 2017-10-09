pedidos-ui
==========

1. Configurar /etc/hosts con pedidos-ui.local
2. Congigurar /etc/apache/httpd.conf con pedidos-ui.local
3. Ejecutar composer update
4. Ejecutar comandos de limpieza y seteo de permisos de var/cache y var/sessions
5. Probar hacer `GET http://pedidos-ui.local/menu` para obtener `Menú 2017, status:Active`
6. bin/console assets:install
7. bin/console assetic:dump