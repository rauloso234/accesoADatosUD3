package org.example;
import javax.persistence.*;
import java.util.*;
@Entity
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "dueño_id")
    private Owner dueño;

    private String raza;  // Nuevo campo para la raza del animal

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL)
    private List<Visita> visitas;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Owner getDueño() {
        return dueño;
    }

    public void setDueño(Owner dueño) {
        this.dueño = dueño;
    }

    public List<Visita> getVisitas() {
        return visitas;
    }

    // Modificar el método setVisitas para asegurar que la lista se actualice automáticamente
    public void setVisitas(List<Visita> visitas) {
        this.visitas = visitas;
        for (Visita visita : visitas) {
            visita.setMascota(this);  // Actualizar la referencia de la mascota en cada visita
        }
    }

    // Método para agregar una nueva visita y asegurar la actualización automática
    public void agregarVisita(Visita visita) {
        if (visitas == null) {
            visitas = new ArrayList<>();
        }
        visitas.add(visita);
        visita.setMascota(this);  // Actualizar la referencia de la mascota en la nueva visita
    }
}