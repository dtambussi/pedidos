<?php
/**
 * Created by PhpStorm.
 * User: martin
 * Date: 10/27/17
 * Time: 2:59 PM
 */

namespace PedidosBundle\Form;


use PedidosBundle\FormEntity\SugerenciaFormEntity;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class SugerenciaForm extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options) {
        $builder->add('nombre', TextType::class, array('label' => 'Nombre:'));
        $builder->add('descripcion', TextType::class, array('label' => 'Descripción:'));
        $builder->add('precio', NumberType::class, ['grouping' => true, 'scale' => 2, 'label' => 'Precio:']);
        $builder->add('cantidad', TextType::class, array('label' => 'Cantidad:'));
        $builder->add('fechaInicio', TextType::class, array('label' => 'Fecha Inicio:'));
        $builder->add('fechaFin', TextType::class, array('label' => 'Fecha Fin:'));
        $builder->add('save', SubmitType::class,array('label' => 'Crear'));
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => SugerenciaFormEntity::class,
        ));
    }

}