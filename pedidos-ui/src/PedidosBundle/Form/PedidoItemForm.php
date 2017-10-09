<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/9/17
 * Time: 12:19 PM
 */

namespace PedidosBundle\Form;


use PedidosBundle\FormEntity\PedidoItemFormEntity;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class PedidoItemForm extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options) {
        $builder->add('cantidad', IntegerType::class, array('label' => 'Cantidad'));
        $builder->add('comentario', TextType::class, array('label' => 'Comentario'));
        $builder->add('id', HiddenType::class);
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => PedidoItemFormEntity::class,
        ));
    }
}