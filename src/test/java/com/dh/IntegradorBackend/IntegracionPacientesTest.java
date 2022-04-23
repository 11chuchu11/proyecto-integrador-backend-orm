package com.dh.IntegradorBackend;

import com.dh.IntegradorBackend.entities.Domicilio;
import com.dh.IntegradorBackend.entities.Odontologo;
import com.dh.IntegradorBackend.entities.Paciente;
import com.dh.IntegradorBackend.service.DomicilioService;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegracionPacientesTest {

    @Autowired
    private DomicilioService domicilioService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test1Cargar() throws Exception{
        Domicilio domicilio = new Domicilio("callePaciente", "localidadPaciente", "provinciaPaciente", 2358);
        Paciente paciente = new Paciente("elPaciente", "muyPaciente", "elpacienteesperapacientemente@muypaciente.com", 576545, domicilio);
        String response = "{\"id\":1,\"nombre\":\"elPaciente\",\"apellido\":\"muyPaciente\",\"email\":\"elpacienteesperapacientemente@muypaciente.com\",\"dni\":576545,\"fechaIngreso\":null,\"domicilio\":{\"id\":1,\"calle\":\"callePaciente\",\"localidad\":\"localidadPaciente\",\"provincia\":\"provinciaPaciente\",\"numero\":2358},\"turnos\":[]}";



        ObjectWriter writer = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
                .writer();

        String pacienteJson = writer.writeValueAsString(paciente);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(response, result.getResponse().getContentAsString());
    }

    @Test
    public void test2Listar() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/pacientes").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assert.assertFalse((result.getResponse().getContentAsString().isEmpty()));
    }

    @Test
    public void test3BuscarPorId()throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/{id}","1").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());

    }
    @Test
    public void test4BuscarPorEmail()throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/email?email={email}","elpacienteesperapacientemente@muypaciente.com").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());

    }

    @Test
    public void test5Actualizar() throws Exception{
        Domicilio domicilio = domicilioService.actualizarDomicilio( new Domicilio(1L,"callePaciente2", "localidadPaciente2", "provinciaPaciente2", 8532));
        Paciente paciente = new Paciente(1L,"elPaciente2", "muyPaciente2", "elpacienteesperapacientemente@muypaciente.com", 545675,domicilio);
        String response = "{\"id\":1,\"nombre\":\"elPaciente2\",\"apellido\":\"muyPaciente2\",\"email\":\"elpacienteesperapacientemente@muypaciente.com\",\"dni\":545675,\"fechaIngreso\":null,\"domicilio\":{\"id\":1,\"calle\":\"callePaciente2\",\"localidad\":\"localidadPaciente2\",\"provincia\":\"provinciaPaciente2\",\"numero\":8532},\"turnos\":[]}";


        ObjectWriter writer = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE,false).writer();
        String pacienteJson = writer.writeValueAsString(paciente);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.put("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(response, result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void test6Borrar() throws Exception{

        String response = "Paciente eliminado";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/pacientes/{id}","1").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(response, result.getResponse().getContentAsString());
    }


}
