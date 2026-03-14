package com.syshotel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.syshotel.entitys.Habitacion;
import com.syshotel.entitys.ProductoServicio;
import com.syshotel.entitys.Usuario;
import com.syshotel.repository.ProductoServicioRepository;
import com.syshotel.repository.UsuarioRepository;

@Service
public class ProductoServiServicie {//Inyección = Acción que hace Spring.

	    @Autowired //spring inyecta osea crea un objeto, un copia de clase productoserviciorepository y permite usar todos sus metods
	     // reproducto variable que recibe la instancia del repositorio
	    private ProductoServicioRepository repoproducto;

	    @Autowired
	    private UsuarioRepository usuarioRepository;
	    
	    public List<ProductoServicio> ListbuscarPorUsername(String username) {
		    return repoproducto.findByUsuarioUsername(username);
	    }
	    //obtener todos los productos de la base de datos el findall()
	    public List<ProductoServicio> listar() {
	        return repoproducto.findAll();
	    }
	    public ProductoServicio registrarMisProductos(ProductoServicio producto,  String username) {
	        
	    	
	    	Usuario usuario = usuarioRepository.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	       producto.setUsuario(usuario);
	    	
	    	return repoproducto.save(producto);
	        
	    }

	   
	    public ProductoServicio registrar(ProductoServicio producto) {
	        return repoproducto.save(producto);
	    }
//METODO PARA OBTENER EL ID DEL PRODCUTO DE LA BASE DE DATOS, POR ESO LA ENTRA INTEGER ID
	    public ProductoServicio obtenerId(@PathVariable  Integer id) {
	        return repoproducto.findById(id).orElse(null);
	    }

	    public void eliminar(Integer id) {
	        repoproducto.deleteById(id);
	    }
	    // OBTENER POR ID Y Actualizar un producto
	    
	    public ProductoServicio actualizar(Integer id, ProductoServicio productoActualizado) { //NUEVO OBJETO A GUARDAR
	   
	    	//1.Busca en la base de datos un celular con el ID recibido.
	    	//2.Usa un Optional para manejar el caso en que no se encuentre ningún registro con ese ID.
	        Optional<ProductoServicio> productoOpt = repoproducto.findById(id);
	        if (productoOpt.isPresent()) {// 3.Verifica si el celular sí fue encontrado en la base de datos.
	            ProductoServicio productoExistente = productoOpt.get();//3.Extrae el objeto Celular encontrado del Optional.
	            // 4.Puedes actualizar los campos aquí si es necesario//Actualiza los campos del objeto existente con los valores nuevos del objeto beanmodi.
	            //toma el nombre que se ha enviada de los parametros a los la variable producto
	            //existente en la base de datso
	            productoExistente.setNombre(productoActualizado.getNombre()); //toma el prodcut y asginado con set
	            productoExistente.setPrecio(productoActualizado.getPrecio());

	            productoExistente.setTipo(productoActualizado.getTipo()); // Si es necesario

	            return repoproducto.save(productoExistente); // Guarda el producto actualizado
	        }
	        return null; // Si no existe el producto, devuelve null*/
	    }
 // Spring Inyección: Spring crea e inyecta automáticamente una instancia(objeto) del repositorio (ProductoServicioRepository) en tu clase, sin que tú tengas que crearla manualmente con new.

// repoproducto: Es el campo donde se almacena la instancia del repositorio inyectado. Spring maneja la creación de este objeto y lo pone a disposición de tu clase.

//Acceso a los métodos: Una vez que Spring ha inyectado el repositorio en el campo repoproducto, puedes usar sus métodos directamente a través de este campo, como si fuera un objeto normal. Esto se debe a que repoproducto almacena la instancia de ProductoServicioRepository, permitiéndote invocar sus métodos como findAll(), save(), findById(), etc.
}
