package org.example;
import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Entity
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Transient
    private String nombreDueño;

    @Transient // Este campo no será persistido en la base de datos
    private String formattedFecha;

    @ManyToOne
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;

    @Transient
    private String motivoVacunacion;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public String getNombreDueño() {
        return nombreDueño;
    }

    public void setNombreDueño(String nombreDueño) {
        this.nombreDueño = nombreDueño;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public String getMotivoVacunacion() {
        return motivoVacunacion;
    }

    public void setMotivoVacunacion(String motivoVacunacion) {
        this.motivoVacunacion = motivoVacunacion;
    }


    public String getFormattedFecha() {
        if (fecha != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return sdf.format(fecha);
        }
        return null;
    }

    public void setFormattedFecha(String formattedFecha) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            this.fecha = sdf.parse(formattedFecha);
        } catch (ParseException e) {
            // Manejar la excepción según tus necesidades, por ejemplo, imprimir un mensaje de error o lanzar una excepción específica.
            e.printStackTrace();
        }
    }
    @PrePersist
    public void prePersist() {
        if (mascota != null && mascota.getDueño() != null) {
            this.nombreDueño = mascota.getDueño().getNombre();
        }
    }
}