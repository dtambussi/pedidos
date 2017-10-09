<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/5/17
 * Time: 12:14 PM
 */

namespace PedidosBundle\Service;


use PedidosBundle\Dto\MenuDto;
use PedidosBundle\Dto\PedidoDto;
use PedidosBundle\Dto\PedidoRequestDto;
use PedidosBundle\Exception\PedidosException;
use Psr\Log\LoggerInterface;
use JMS\Serializer\Serializer;
use Symfony\Component\HttpFoundation\Response;

class PedidosApiHttpClient
{
    const SERVICE_NAME = "pedidosapi_http_client";
    /**
     * @var LoggerInterface
     */
    private $logger;

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
    public function __construct($logger, $pedidosapiHostname, $serializer)
    {
        $this->logger = $logger;
        $this->serializer = $serializer;
        $this->pedidosapiHostname = $pedidosapiHostname;
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

        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        $json = curl_exec($ch);
        $responseCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);

        if (in_array($responseCode, $expectedCodes)) {
            $this->logger->info("Ok!");
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

        if (is_object($postBody)) {
            $postJson = $this->serializer->serialize($postBody, "json");
            curl_setopt($ch, CURLOPT_HTTPHEADER, array(
                    'Content-Type: application/json',
                    'Content-Length: ' . strlen($postJson))
            );
            curl_setopt($ch, CURLOPT_POSTFIELDS, $postJson);

        } else {
            curl_setopt($ch, CURLOPT_POSTFIELDS, $postBody);

        }

        $responseString = curl_exec($ch);
        $responseCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);

        if (in_array($responseCode, $expectedCodes)) {
            $this->logger->info("Ok!");
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