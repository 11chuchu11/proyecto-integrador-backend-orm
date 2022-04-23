window.addEventListener('load', function () {
  (function(){


    const url = '/turnos';
    const settings = {
      method: 'GET'
    }

    fetch(url,settings)
    .then(function(respuesta){
          return respuesta.json();
          })
    .then(function (info) {
      console.log(info);
for(let i=0;i<info.length;i++){
      var table = document.getElementById("turnoTable");
      var turnoRow =table.insertRow();
      let tr_id = 'tr_' + info[i].id;
      turnoRow.id = tr_id;

      turnoRow.innerHTML = `<td class=\"td_paciente\">${info[i].paciente.nombre} ${info[i].paciente.apellido} </td>
                              <td class=\"td_odontologo\">${info[i].odontologo.nombre} ${info[i].odontologo.apellido} </td>
                              <td class=\"td_fecha\">${info[i].fecha}</td>`;
  };

})
})

(function(){
let pathname = window.location.pathname;
if (pathname == "/turnosLista.html") {
    document.querySelector(".nav .nav-item a:last").addClass("active");
}
})
})