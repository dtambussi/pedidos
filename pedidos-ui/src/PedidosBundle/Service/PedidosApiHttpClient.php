<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/5/17
 * Time: 12:14 PM
 */

namespace PedidosBundle\Service;


use PedidosBundle\Dto\MenuDto;
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
        $url = "http://" . $this->pedidosapiHostname . "/menu";
        $response = $this->doGet($url, MenuDto::class);
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
}