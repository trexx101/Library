$ ->
  $.get "/", (data) ->
    $.each data, (index, resource) ->
      $("#stuff").append $("<li>").text resource.name