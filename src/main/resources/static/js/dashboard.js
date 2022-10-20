
(() => {
  'use strict'

  feather.replace({ 'aria-hidden': 'true' })

  $(function () {
    $("#customer-input").autocomplete({
      source: function (request, response) {
        $.ajax({
          url: "/waybill/customer/autocomplete",
          dataType: "json",
          data: {
            term: request.term
          },
          success: function (res) {
            let result;
            result = $.map(res, function (obj){
              return {
                label: [obj.customerName, obj.address, obj.postcode, obj.city, obj.vatNumber].join(', '),
                value: obj.customerName,
                data: obj
              };
            });
            response(result);
          },
        });
      },
      select: function (event, selectedData) {
        let customer = selectedData.item.data;
        console.log(customer);
        $('#customer-id').val(customer.id);
        $('#customer-address').val(customer.address);
        $('#customer-postcode').val(customer.postcode);
        $('#customer-city').val(customer.city);
        $('#customer-vat').val(customer.vatNumber);
      }
    });
  });



  $(function () {
    $("#customer-input").autocomplete({
      source: function (request, response) {
        $.ajax({
          url: "/waybill/customer/autocomplete",
          dataType: "json",
          data: {
            term: request.term
          },
          success: function (res) {
            let result;
            result = $.map(res, function (obj){
              return {
                label: [obj.customerName, obj.address, obj.postcode, obj.city, obj.vatNumber].join(', '),
                value: obj.customerName,
                data: obj
              };
            });
            response(result);
          },
        });
      },
      select: function (event, selectedData) {
        let customer = selectedData.item.data;
        console.log(customer);
        $('#customer-id').val(customer.id);
        $('#customer-address').val(customer.address);
        $('#customer-postcode').val(customer.postcode);
        $('#customer-city').val(customer.city);
        $('#customer-vat').val(customer.vatNumber);
      }
    });
  })
})()
