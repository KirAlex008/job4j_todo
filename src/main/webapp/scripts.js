$('document').ready(function () {

    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/todo/all_tasks.do',
        dataType: 'json'
    }).done(function (data) {
        for (const task of data) {
            let idVal = task.id;
            let doneVal = task.done;
            let lineCheckBox = getCheckBox(doneVal, idVal);
            let categoriesArray= [];
            for (const c of task.categories) {
                categoriesArray.push(c.name)
            }
            $('#insert').append(`<tr>
                        + <td> ${task.id} </td>
                        + ${lineCheckBox}
                        + <td> ${task.description} </td>
                        + <td> ${task.created} </td>
                        + <td> ${categoriesArray} </td>
                        + </tr>`);
        }
    }).fail(function(err){
        alert(err.text);
    }); // end getAllTasks

    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/todo/save.do',
        dataType: 'json'
    }).done(function (data) {
        let categories = "";
        for (let i = 0; i < data.length; i++) {
            console.log("Go")
            categories += "<option value=" + data[i]['id'] + ">" + data[i]['name'] + "</option>";
        }
        $('#cIds').html(categories);
    }).fail(function(err){
        alert(err.responseText);
    }) // end loading of categories schedule

    $('#button').click(function () {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/todo/save.do',
            data: JSON.stringify({
                text: $('#exampleInputTask1').val()
            }),
            dataType: 'json',
        }).fail(function(err){
            alert(err.responseText);

        });
    }); // end addNewTask

    $('#insert').on('click', '.box', function() {
        let idVal;
        let doneBox;
        if ($(this).is(':checked')){
            idVal = $(this).attr('id');
            console.log(idVal)
            $.ajax({
                type: 'POST',
                contentType: 'application/json',
                dataType: 'json',
                url: 'http://localhost:8080/todo/update.do',
                data: JSON.stringify(
                    {idVal : idVal}
                ),
            }).done(function (data) {
                console.log(data.text)
            }).fail(function(err){
                    alert(err.responseText);
                });

        } else {
            idVal = $(this).attr('id');
            console.log(idVal)
            $.ajax({
                type: 'POST',
                contentType: 'application/json',
                dataType: 'json',
                url: 'http://localhost:8080/todo/update.do',
                data: JSON.stringify({idVal : idVal}),
            }).done(function (data) {
                console.log(data.text)
            }).fail(function(err){
                alert(err.responseText);
            });
        }
    }); // end insertRowsIntoTable

    $(document).on('click','#allTasks', function() {
        if ($(this).prop('checked')) {
            $('.box').each(function(){
                if ($(this).prop('checked')) {
                    $(this).parents("tr").hide();
                } // end if
            }); // end func
        } else {
            $('.box').each(function () {
                $(this).parents("tr").show();
            }); // end func
        } // end else
    }); // end hideAllCompleteTasks

    function getCheckBox(done, id) {
        let result;
        if (done === true) {
            result = `<td> <input type="checkbox" class="box" id=${id} checked> </td>`;
        } else {
            result = `<td> <input type="checkbox" class="box" id=${id} > </td>`;
        }
        return result
    } // end getCheckBox

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/name.do',
        dataType: 'text'
    }).done(function(data) {
        $('#result').append(data);
    }).fail(function(err){
        alert(err.responseText);
    }); // end loading of user name

}); // end ready



