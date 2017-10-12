pedidos-ui
==========

1. Configurar /etc/hosts con pedidos-ui.local
2. Congigurar /etc/apache/httpd.conf con pedidos-ui.local
3. Ejecutar composer update
4. Ejecutar comandos de limpieza y seteo de permisos de var/cache y var/sessions
5. Probar hacer `GET http://pedidos-ui.local/menu` para obtener `Menú 2017, status:Active`
6. bin/console assets:install
7. bin/console assetic:dump

Tambussi
==========

1. Bajar el proyecto y hacer checkout de release-1.0. Para el ejemplo me paré en /tmp e hize git clone.
Tener en cuenta que apache va a necesitar leer y escribir en esa carpeta.
2. Es necesario dar permisos de escritura al apache para que pueda crear la carpeta cache. Parado en `/tmp/pedidos/pedidos-ui/` ejecutar:

Mac
```bash
sudo chmod -R +a "_www allow delete,write,append,file_inherit,directory_inherit" var/cache var/logs var/sessions
sudo chmod -R +a "`whoami` allow delete,write,append,file_inherit,directory_inherit" var/cache var/logs var/sessions
```

Ubuntu
```bash
sudo setfacl -R -m u:www-data:rwx -m u:`whoami`:rwx var/cache var/logs var/sessions
sudo setfacl -dR -m u:www-data:rwx -m u:`whoami`:rwx var/cache var/logs var/sessions
```

3. Configurar `/etc/hosts` con la url deseada. En este caso, tambussi.local
```bash
127.0.0.1 tambussi.local
```

4. Agregar el virtual host para apuntar a la carpeta donde bajamos el proyecto, subcarpeta `/web`.

```bash
/etc/apache/httpd.conf
...
<VirtualHost *:80>
    ServerName tambussi.local
    DocumentRoot "/tmp/pedidos/pedidos-ui/web"
    php_value newrelic.appname "pedidos"

    <Directory "/tmp/pedidos/pedidos-ui/web">
        # enable the .htaccess rewrites
        AllowOverride All
        Order allow,deny
        Allow from All
        Require all granted
    </Directory>

    ErrorLog /var/log/apache2/tambussi.error.log
    CustomLog /var/log/apache2/tambussi.access.log combined
</VirtualHost>
...
```

5. Ir a la home de la aplicación `http://tambussi.local/menu`. Eso es todo
