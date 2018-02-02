using ServicioWeb.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ServicioWeb
{
    interface ICliente
{

         List<GesCliente> ObtenerCliente(String correo);
         List<GesCliente> obtenerclienteInicial(int idcliente);
         String insertarCliente(SegUsuario cliente);
         int iniciarSesion(String email, String clave);
         String actualizarUbicacion(double latitud, double longitud, String correo);


    }
}
