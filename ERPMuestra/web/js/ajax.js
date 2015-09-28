
$(function (){
    $("#home").click(function (){
        location.reload(); 
    });
    $("#alta").click(function (){
        $("#der").load("faces/altaProyecto.xhtml"); 
    });
    $("#list").click(function (){
        $("#der").load("faces/listaProyectos.xhtml");
    });
    $("#preContrt").click(function (){
        $("#der").load("faces/precontrato.xhtml");
    });
    $("#edPreCntrt").click(function (){
        $("#der").load("faces/editarContto.xhtml");
    });
    $("#cntrto").click(function (){
        $("#der").load("faces/contrato.xhtml");
    });
    $("#avan").click(function (){
        $("#der").load("faces/avance.xhtml");
    });
    $("#estimac").click(function (){
        $("#der").load("faces/estimacion.xhtml");
    });
    $("#requis").click(function (){
        $("#der").load("faces/solrequisicion.xhtml");
    });
    $("#autRequis").click(function (){
        $("#der").load("faces/listadoRequisicion.xhtml");
    });
    $("#orCompr").click(function (){
        $("#der").load("faces/solordencompra.xhtml");
    });
    $("#autOrComp").click(function (){
        $("#der").load("faces/listadoOrdenCompra.xhtml");
    });
    $("#recepc").click(function (){
        $("#der").load("faces/recepcion.xhtml");
    });
    $("#lRecepc").click(function (){
        $("#der").load("faces/listarRecepciones.xhtml");
    });
    $("#devoluc").click(function (){
        $("#der").load("faces/devolucion.xhtml");
    });
    $("#listDevoluc").click(function (){
        $("#der").load("faces/listarDevoluciones.xhtml");
    });
    $("#vCons").click(function (){
        $("#der").load("faces/valeconsumo.xhtml");
    });
    $("#lVCons").click(function (){
        $("#der").load("faces/listarValeConsumo.xhtml");
    });
    $("#vDevol").click(function (){
        $("#der").load("faces/valedevolucion.xhtml");
    });
    $("#inventa").click(function (){
        $("#der").load("faces/inventario.xhtml");
    });
    $("#fReg").click(function (){
        $("#der").load("faces/facturas.xhtml");
    });
    $("#remesa").click(function (){
         $("#der").load("faces/cuentasxPag.xhtml");
    });
    $("#repRem").click(function (){
        $("#der").load("faces/reporteCXP.xhtml");
    });
    $("#vPresP").click(function (){
        $("#der").load("faces/vista2.xhtml");
    });
    $("#expl").click(function (){
        $("#der").load("faces/expInsumos.xhtml");
    });
    $("#fList").click(function (){
        $("#der").load("faces/listaFacturas.xhtml");
    });
    $("#agrConc").click(function (){
        $("#der").load("faces/creaConcepto.xhtml");
    });
    $("#pCrea").click(function (){
        $("#der").load("faces/altaPrespto.xhtml"); 
    });
    $("#pPro").click(function (){
        $("#der").load("faces/cargaPres.xhtml"); 
    });
   
});

