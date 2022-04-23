package com.dh.IntegradorBackend;



import com.dh.IntegradorBackend.entities.Domicilio;
import com.dh.IntegradorBackend.entities.Odontologo;
import com.dh.IntegradorBackend.entities.Paciente;
import com.dh.IntegradorBackend.entities.Turno;
import com.dh.IntegradorBackend.service.DomicilioService;
import com.dh.IntegradorBackend.service.OdontologoService;
import com.dh.IntegradorBackend.service.PacienteService;
import com.dh.IntegradorBackend.service.TurnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegracionTurnosTest {

    @Autowired
    private TurnoService turnoService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private DomicilioService domicilioService;
    @Autowired
    private MockMvc mockMvc;


    public void cargarDatosEnBd(){
        domicilioService.registrarDomicilio(new Domicilio("callePaciente", "localidadPaciente", "provinciaPaciente", 3245));
        pacienteService.guardarPaciente(new Paciente("Paciente", "altoPaciente", "elmaildelpaciente@gmail.com", 2342365, LocalDate.of(2022, 04, 15), domicilioService.buscarDomicilio(1L).get() ));
        odontologoService.guardarOdontologo(new Odontologo("Juan" ,"Juanes",4234));
        turnoService.guardarTurno(new Turno(pacienteService.buscarPaciente(1L).get(), odontologoService.buscarOdontologo(1L).get(), LocalDate.of(2022,04,21)));
    }

    @Test
    public void test1Cargar() throws Exception{

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
                .writer();



        Domicilio domicilioPaciente = new Domicilio("callePaciente", "localidadPaciente", "provinciaPaciente", 3245);
        Paciente paciente = new Paciente("Paciente","altoPaciente","elmaildelpaciente@gmail.com",2342365,domicilioPaciente);
        Odontologo odontologo = new Odontologo( "Pepe", "Pepardo", 45764);
        Domicilio domicilioPacienteCargado = new Domicilio(1L,"callePaciente", "localidadPaciente", "provinciaPaciente", 3245);
        Paciente pacienteCargado = new Paciente(1L,"Paciente","altoPaciente","elmaildelpaciente@gmail.com",2342365,domicilioPacienteCargado);
        Odontologo odontologoCargado = new Odontologo(1L, "Pepe", "Pepardo", 45764);

        String pacienteCargaJson = writer.writeValueAsString(paciente);
        String odontologoCargaJson = writer.writeValueAsString(odontologo);

        MvcResult resultPaciente = mockMvc.perform(MockMvcRequestBuilders.post("/pacientes").contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteCargaJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        MvcResult resultOdontologo = mockMvc.perform(MockMvcRequestBuilders.post("/odontologos").contentType(MediaType.APPLICATION_JSON)
                        .content(odontologoCargaJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Turno turno = new Turno(pacienteCargado,odontologoCargado);
        String response = "{\"id\":1," +
                "\"paciente\":{\"id\":1,\"nombre\":\"Paciente\",\"apellido\":\"altoPaciente\",\"email\":\"elmaildelpaciente@gmail.com\",\"dni\":2342365,\"fechaIngreso\":null,\"" +
                "domicilio\":{\"id\":1,\"calle\":\"callePaciente\",\"localidad\":\"localidadPaciente\",\"provincia\":\"provinciaPaciente\",\"numero\":3245}}," +
                "\"odontologo\":{\"id\":1,\"nombre\":\"Pepe\",\"apellido\":\"Pepardo\",\"matricula\":45764}," +
                "\"fecha\":null}";


        String turnoJson = writer.writeValueAsString(turno);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(turnoJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(response, result.getResponse().getContentAsString());
    }

    @Test
    public void test2listar()throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/turnos")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void test3BuscarPorId()throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/turnos/{id}","1").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());

    }

    @Test
    public void test5Actualizar() throws Exception{

        ObjectWriter writer = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE,false).writer();


        Odontologo odontologonActualizado = new Odontologo( "ElOdontologo", "altoCrack", 4556);
        Domicilio domicilioPacienteCargado = new Domicilio(1L,"callePaciente", "localidadPaciente", "provinciaPaciente", 3245);
        Paciente pacienteCargado = new Paciente(1L,"Paciente","altoPaciente","elmaildelpaciente@gmail.com",2342365,domicilioPacienteCargado);
        Odontologo odontologoCargado = new Odontologo(2L, "ElOdontologo", "altoCrack", 4556);

        String odontologoCargaJson = writer.writeValueAsString(odontologonActualizado);


        MvcResult resultOdontologo = mockMvc.perform(MockMvcRequestBuilders.post("/odontologos").contentType(MediaType.APPLICATION_JSON)
                        .content(odontologoCargaJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Turno turno = new Turno(1L,pacienteCargado,odontologoCargado);
        String response = "{\"id\":1," +
                "\"paciente\":{\"id\":1,\"nombre\":\"Paciente\",\"apellido\":\"altoPaciente\",\"email\":\"elmaildelpaciente@gmail.com\",\"dni\":2342365,\"fechaIngreso\":null,\"" +
                "domicilio\":{\"id\":1,\"calle\":\"callePaciente\",\"localidad\":\"localidadPaciente\",\"provincia\":\"provinciaPaciente\",\"numero\":3245}}," +
                "\"odontologo\":{\"id\":2,\"nombre\":\"ElOdontologo\",\"apellido\":\"altoCrack\",\"matricula\":4556}," +
                "\"fecha\":null}";

        String turnoJson = writer.writeValueAsString(turno);


        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.put("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(turnoJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(response, result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getContentAsString());
    }
    @Test
    public void test6Borrar() throws Exception{

        String response = "Turno eliminado";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/turnos/{id}","1").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(response, result.getResponse().getContentAsString());
    }


}
