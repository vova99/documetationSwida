function showCutModal(btnObj) {

    var trObj = btnObj.parentElement.parentElement;
    var trId =  $(trObj).attr('id');
    $('#idOfTreeStorageRow').val(trId);
    console.log(  $(trObj).attr('id'));
    var codeOfTreeStorage = $(trObj).find('th:eq(0)').text();
    $('#addRawStorageCode').val(codeOfTreeStorage);

    $('#cutModal').modal('show');
}

function showEditModal(btnObj) {
    var trObj = btnObj.parentElement.parentElement;
    var trId =  $(trObj).attr('id');
    $('#incomeTreeStorageId').val(trId);

    var code =          $(trObj).find('th:eq(0)').text();
    var breed =         $(trObj).find('td:eq(1)').text();
    var desc =          $(trObj).find('td:eq(2)').text();
    var provider =      $(trObj).find('td:eq(3)').text();
    var providerId =    $(trObj).find('td:eq(4)').text();
    var avgDiameter =   $(trObj).find('td:eq(5)').text();
    var amount =        $(trObj).find('td:eq(6)').text();
    var extent =        $(trObj).find('td:eq(7)').text();
    var date =          $(trObj).find('td:eq(8)').text();

    $('#editIncomeCode')            .val(code);
    $('#editIncomeMaterial')        .val(breed);
    $('#editIncomeMaterialDesc')    .val(desc);
    $('#editIncomeSupplier')        .val(provider);
    $('#editIncomeSupplier-hidden') .val(providerId);
    $('#editAverageDiameter')       .val(avgDiameter);
    $('#editAmount')                .val(amount);
    $('#editIncomeVolume')          .val(extent);
    $('#editIncomeDate')            .val(date);

    $('#editIncome').modal('show');
}