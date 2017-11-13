<?php

use Symfony\Component\HttpFoundation\Request;

if ($_SERVER['QUERY_STRING']=="assets") {
    echo "Instalando assets...\n";
    var_dump(shell_exec("bash ../assets.sh 2>&1"));
    exit;
}

require __DIR__.'/../vendor/autoload.php';
if (PHP_VERSION_ID < 70000) {
    include_once __DIR__.'/../var/bootstrap.php.cache';
}
date_default_timezone_set('America/Argentina/Buenos_Aires');
$kernel = new AppKernel('dev', true);
if (PHP_VERSION_ID < 70000) {
    $kernel->loadClassCache();
}
//$kernel = new AppCache($kernel);

// When using the HttpCache, you need to call the method in your front controller instead of relying on the configuration parameter
//Request::enableHttpMethodParameterOverride();
$request = Request::createFromGlobals();
$response = $kernel->handle($request);
$response->send();
$kernel->terminate($request, $response);
