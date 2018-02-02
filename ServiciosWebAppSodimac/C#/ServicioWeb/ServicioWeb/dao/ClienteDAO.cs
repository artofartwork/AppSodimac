using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ServicioWeb.model;

namespace ServicioWeb.dao
{
    class ClienteDAO : ICliente
    {
        public string actualizarUbicacion(double latitud, double longitud, string correo)
        {
            throw new NotImplementedException();
        }

        public int iniciarSesion(string email, string clave)
        {
            int res = 0;
            

                DataClasses1DataContext db = new DataClasses1DataContext();
                var usuario = from p in db.SegUsuario where p.Usuario == email select p;
                var pass = from p in db.SegUsuario where p.Clave == clave select p;


                if (usuario.Any() && pass.Any())
                {

                    res = 1;
            }
                return res;
        }

        public string insertarCliente(SegUsuario cliente)
        {
            throw new NotImplementedException();
        }

        public List<GesCliente> ObtenerCliente(string correo)
        {
            throw new NotImplementedException();
        }

        public List<GesCliente> obtenerclienteInicial(int idcliente)
        {
            throw new NotImplementedException();
        }
    }
}
