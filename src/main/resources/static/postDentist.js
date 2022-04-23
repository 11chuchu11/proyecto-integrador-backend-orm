const form = document.querySelector('#formDentist')
const inputName = document.querySelector('#nameDentist')
const inputSurname = document.getElementById('surnameDentist')
const inputRegister = document.getElementById('registerDentist')
const submitButton = document.getElementById('submitButton')
const boxUploadedDentist = document.getElementById('uploadedDentist')

const name = inputName.value
const surname = inputSurname.value
const register = inputRegister.value



form.addEventListener('submit', (e) => {
    e.preventDefault()
    if(name === '' || surname === '' || register === ''){
        alert('Por favor, complete todos los campos')
    }

}


