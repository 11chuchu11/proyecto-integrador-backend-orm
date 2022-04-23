window.addEventListener('load', function () {
    (function(){


      const url = '/pacientes';
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
        var table = document.getElementById("pacienteTable");
        var pacienteRow =table.insertRow();
        let tr_id = 'tr_' + info[i].id;
        pacienteRow.id = tr_id;

        pacienteRow.innerHTML = `<td class=\"td_nombre\">${info[i].nombre.toUpperCase()}</td>
                                <td class=\"td_apellido\">${info[i].apellido.toUpperCase()}</td>
                                <td class=\"td_email\">${info[i].email}</td>
                                <td class=\"td_Dni\">${info[i].dni}</td>`;
    };

})
})

(function(){
  let pathname = window.location.pathname;
  if (pathname == "/pacientesLista.html") {
      document.querySelector(".nav .nav-item a:last").addClass("active");
  }
})
})