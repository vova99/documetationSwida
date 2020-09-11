function sendForPackagesStorage(btnObj) {

    var trObj = btnObj.parentElement.parentElement;
    var trId =  $(trObj).attr('id');
    $('#sendForPackagesModalId').val(trId);
    console.log(  $(trObj).attr('id'));
    var codeOfRawStorage = $(trObj).find('th:eq(0)').text();
    $('#sendForPackageModalHeader').text("Формирование пачек из  "+codeOfRawStorage);

    var code = 			$(trObj).find('th:eq(0)').text();
    var material = 		$(trObj).find('td:eq(0)').text();
    var description = 	$(trObj).find('td:eq(1)').text();
    var thickness = 	parseInt($(trObj).find('td:eq(2)').text());
    var width = 		parseInt($(trObj).find('td:eq(3)').text());
    var length = 		parseInt($(trObj).find('td:eq(4)').text());
    var count = 		parseInt($(trObj).find('td:eq(5)').text());
    var extent = 		$(trObj).find('td:eq(6)').text();

    $('#sendForPackageModalCode')			.val(code);
    $('#sendForPackageModalMaterial')		.val(material);
    $('#sendForPackageModalMaterialDescr')	.val(description);
    $('#sendForPackageModalThickness')		.val(thickness);
    $('#sendForPackageModalWidth')			.val(width);
    $('#sendForPackageModalLength')			.val(length);
    $('#sendForPackageModalCount')			.val(count);
    $('#sendForPackageModalVolume')			.val(extent);

    $("#sendForPackageHeightPc").focus();
    $('#sendForPackageModal').modal('show');


    // CHECK
    $("#sendForPackageHeightPc").change(function(){
        if ((parseInt($('#sendForPackageHeightPc').val()))<=0) {
            alert("Введите правильное значение");
            $('#sendForPackageHeightPc').val("1");
        }else{

        }
    });

    $("#sendForPackageModalWidthPc").change(function(){
        if ((parseInt($('#sendForPackageModalWidthPc').val()))<=0) {
            alert("Введите правильное значение");
            $('#sendForPackageModalWidthPc').val("1");
        }else{

        }
    });

    $("#sendForPackageModalLengthFact").change(function(){
        if ((parseInt($('#sendForPackageModalLengthFact').val()))<=0) {
            alert("Введите правильное значение");
            $('#sendForPackageModalLengthFact').val("1");
        }else{

        }
    });

    $("#sendForPackageModalPacksCount").change(function(){
        if ((parseInt($('#sendForPackageModalPacksCount').val()))<=0) {
            alert("Введите правильное значение");
            $('#sendForPackageModalPacksCount').val("1");
        }else{
            var heightPc = 		parseInt($('#sendForPackageHeightPc').val());
            var widthPc = 		parseInt($('#sendForPackageModalWidthPc').val());
            var amount = 		heightPc*widthPc*(parseInt($('#sendForPackageModalPacksCount').val()));
            var maxAmount =		count;
            var packsCt = 		parseInt($('#sendForPackageModalPacksCount').val());
            var luck =			amount-maxAmount;
            if (amount>maxAmount) {
                alert("Не хватает досок: "+luck+"!");
                $('#sendForPackageModalPacksCount').val("");
            }
        }
    });
    // CHECK
}

function editDryStorage(btnObj) {

    var trObj = btnObj.parentElement.parentElement;
    var trId =  $(trObj).attr('id');
    $('#editDryStorageModalId').val(trId);
    console.log(  $(trObj).attr('id'));
    var codeOfRawStorage = $(trObj).find('th:eq(0)').text();
    $('#editDryStorageModalHeader').text("Редактировать "+codeOfRawStorage);

    var code = $(trObj).find('th:eq(0)').text();
    var material = $(trObj).find('td:eq(0)').text();
    var description = $(trObj).find('td:eq(1)').text();
    var thickness = $(trObj).find('td:eq(2)').text();
    var width = $(trObj).find('td:eq(3)').text();
    var length = $(trObj).find('td:eq(4)').text();
    var count = $(trObj).find('td:eq(5)').text();
    var extent = $(trObj).find('td:eq(6)').text();

    $('#editDryStorageModalCode').val(code);
    $('#editDryStorageModalMaterial').val(material);
    $('#editDryStorageModalMaterialDescr').val(description);
    $('#editDryStorageModalThickness').val(thickness);
    $('#editDryStorageModalWidth').val(width);
    $('#editDryStorageModalLength').val(length);
    $('#editDryStorageModalCount').val(count);
    $('#editDryStorageModalVolume').val(extent);


    $('#editDryStorageModal').modal('show');
}