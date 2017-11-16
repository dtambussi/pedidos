<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/5/17
 * Time: 12:14 PM
 */

namespace PedidosBundle\Service;


use PedidosBundle\Dto\MenuDto;
use PedidosBundle\Dto\ReportePedidosDto;
use PedidosBundle\Dto\Request\CambiarEstadoDePedidoRequest;
use PedidosBundle\Dto\Request\LoginGuestRequestDto;
use PedidosBundle\Dto\Request\LoginRequestDto;
use PedidosBundle\Dto\Request\LoginUsuarioRegistradoRequestDto;
use PedidosBundle\Dto\Request\RecibirPedidoRequest;
use PedidosBundle\Dto\Request\ReportePedidosRequestDto;
use PedidosBundle\Dto\Request\SugerenciaRequestDto;
use PedidosBundle\Dto\Response\PedidoDto;
use PedidosBundle\Dto\Request\PedidoRequestDto;
use PedidosBundle\Dto\SessionDeUsuarioDto;
use PedidosBundle\Dto\SugerenciaDto;
use PedidosBundle\Dto\UsuarioDto;
use PedidosBundle\Exception\PedidosException;
use Psr\Log\LoggerInterface;
use JMS\Serializer\Serializer;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\Session;

class PedidosApiHttpClient
{
    const SERVICE_NAME = "pedidosapi_http_client";

    /**
     * @var LoggerInterface
     */
    private $logger;

    /** @var  Session */
    private $session;

    /**
     * @var Serializer
     */
    private $serializer;
    private $pedidosapiHostname;

    /**
     * Service constructor.
     * @param $logger
     * @param $serializer
     * @param $pedidosapiHostname
     */
    public function __construct($logger, $pedidosapiHostname, $serializer,$session)
    {
        $this->logger = $logger;
        $this->serializer = $serializer;
        $this->pedidosapiHostname = $pedidosapiHostname;
        $this->session = $session;
    }

    /**
     * @return MenuDto
     */
    public function findMenu() {
        $url = "http://" . $this->pedidosapiHostname . "/menu?menu.status=Active";
        $response = $this->doGet($url, MenuDto::class);
        return $response[0];
    }

    /**
     * @return array<SugerenciaDto>
     */
    public function findSugerencias() {
        $url = "http://" . $this->pedidosapiHostname . "/sugerenciasVigentes";
        $response = $this->doGet($url, "array<".SugerenciaDto::class.">");
        return $response[0];
    }


    /**
     * @return array<PedidoDto>
     */
    public function findPedidos() {
        $url = "http://" . $this->pedidosapiHostname . "/pedidos";
        $response = $this->doGet($url, "array<" . PedidoDto::class . ">");
        return $response[0];
    }

    /**
     * @return PedidoRequestDto
     */
    public function confirmarPedido(PedidoRequestDto $pedidoRequestDto) {
        $url = "http://" . $this->pedidosapiHostname . "/pedidos";
        $response = $this->doPost($url, PedidoDto::class, $pedidoRequestDto);
        return $response[0];
    }


    /**
     * @return SessionDeUsuarioDto
     */
    public function doLogin($userEmail, $password) {
        $url = "http://" . $this->pedidosapiHostname . "/loginUsuarioRegistrado";
        $loginRequest = new LoginRequestDto($userEmail, $password);
        $response = $this->doPost($url, SessionDeUsuarioDto::class, $loginRequest);
        return $response[0];
    }

    /**
     * @return SessionDeUsuarioDto
     */
    public function doLoginGuest($nickname) {
        $url = "http://" . $this->pedidosapiHostname . "/loginUsuarioNoRegistrado";
        $loginRequest = new LoginGuestRequestDto($nickname);
        $response = $this->doPost($url, SessionDeUsuarioDto::class, $loginRequest);
        return $response[0];
    }

    /**
     * @return SugerenciaDto
     */
    public function generarSugerencia(SugerenciaRequestDto $sugerenciaRequestDto) {
        $url = "http://" . $this->pedidosapiHostname . "/sugerencias";
        $response = $this->doPost($url, SugerenciaDto::class, $sugerenciaRequestDto);
        return $response[0];
    }


    /**
     * @return ReportePedidosDto
     */
    public function generarReportePedidos(ReportePedidosRequestDto $reporteRequestDto){
        $url = "http://" . $this->pedidosapiHostname . "/reporteDePedidosRequest";
        $response = $this->doPost($url, ReportePedidosDto::class, $reporteRequestDto);
        return $response[0];
    }

    /**
     * @param CambiarEstadoDePedidoRequest $cambiarEstadoPedidoRequest
     */
    public function cambiarEstadoDePedido(CambiarEstadoDePedidoRequest $cambiarEstadoPedidoRequest)
    {
        $url = "http://" . $this->pedidosapiHostname . "/pedido/" . $cambiarEstadoPedidoRequest->getIdPedido() . "/cambiarEstadoDePedidoRequest";
        $response = $this->doPost($url, null, $cambiarEstadoPedidoRequest);
        return $response[0];
    }

    /**
     * @deprecated No se debe usar. Usar solo cambiarEstadoDePedido
     * @param $recibirPedidoRequest
     */
    public function recibirPedido(RecibirPedidoRequest $recibirPedidoRequest)
    {
        $url = "http://" . $this->pedidosapiHostname . "/pedido/" . $recibirPedidoRequest->getIdPedido() . "/recibirPedidoRequest";
        $response = $this->doPost($url, null, $recibirPedidoRequest);
        return $response[0];
    }

    public function logout()
    {
        $url = "http://" . $this->pedidosapiHostname . "/logout";
        $this->doPost($url, null, null);
    }

    /**
     * @param $url
     * @param $mappingClassName
     * @param array|int $expectedCodes
     * @return array [0]: Respuesta json [1] response code
     * @throws PedidosException
     */
    private function doGet($url, $mappingClassName, $expectedCodes = null) {

        if (!$expectedCodes) {
            $expectedCodes = array(Response::HTTP_OK);
        }
        if (!is_array($expectedCodes)) {
            $expectedCodes = array($expectedCodes);
        }

        $ch = curl_init($url);

        /** @var UsuarioDto $usuarioDto */
        $usuarioDto = $this->session->get(UsuarioDto::SESSION_NAME);

        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        if(!is_null($usuarioDto)){
            curl_setopt($ch, CURLOPT_HTTPHEADER, array(
                    'AuthorizationPedidos: ' . $usuarioDto->getSessionId())
            );
        }

        $json = curl_exec($ch);
        $responseCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);


        if (in_array($responseCode, $expectedCodes)) {
            $this->logger->info("$url: Ok!");
        } else {
            throw new PedidosException("Error al llamar al servicio con url $url");
        }

        $jsonObject = $this->serializer->deserialize($json, $mappingClassName, "json");
        return [$jsonObject, $responseCode];
    }


    /**
     * @param $url
     * @param $mappingClassName
     * @param string|object $postBody Ej: email=blabla&password=blabla or php object if it is a json post
     * @param array|int $expectedCodes Puede ser un array o no, soporta ambos
     * @return array [0]: Respuesta json si $mappingClassName != null, respuesta string ei $mappingClassName == null
     * [0]: Respuesta json si $mappingClassName != null, respuesta string ei $mappingClassName == null
     * [1]: Response code (404, 200, 422, etc).
     * @throws PedidosException
     */
    private function doPost($url, $mappingClassName, $postBody, $expectedCodes = null) {

        if (!$expectedCodes) {
            $expectedCodes = array(Response::HTTP_OK);
        }
        if (!is_array($expectedCodes)) {
            $expectedCodes = array($expectedCodes);
        }

        $ch = curl_init($url);

        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");

        /** @var UsuarioDto $usuarioDto */
        $usuarioDto = $this->session->get(UsuarioDto::SESSION_NAME);

        if ($postBody && is_object($postBody)) {
            $postJson = $this->serializer->serialize($postBody, "json");
            $headerParams = array(
                'Content-Type: application/json',
                'Content-Length: ' . strlen($postJson));

            if(!is_null($usuarioDto)){
                array_push($headerParams,'AuthorizationPedidos: ' . $usuarioDto->getSessionId());
            }

            curl_setopt($ch, CURLOPT_HTTPHEADER, $headerParams);
            curl_setopt($ch, CURLOPT_POSTFIELDS, $postJson);
        } else {
            curl_setopt($ch, CURLOPT_POSTFIELDS, $postBody);
        }

        $responseString = curl_exec($ch);
        $responseCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);

        if (in_array($responseCode, $expectedCodes)) {
            $this->logger->info("$url: Ok!");
        } else {
            throw new PedidosException("Error al llamar al servicio con url $url");
        }

        $response = $responseString;
        if ($mappingClassName) {
            $response = $this->serializer->deserialize($responseString, $mappingClassName, "json");
        }

        return [$response, $responseCode];
    }

}