app =(function (){
    function login(){
        apiclient.conexion(cargarDatos);
    }
    function cargarDatos(data) {
		$("#titulo").append(data)
	}
    return{
        conexion:login
    }
})();