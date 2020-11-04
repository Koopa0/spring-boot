/* Search Bar -----------------------------------------------------*/

//fixed position
$(window).scroll(function () {
  if ($(window).scrollTop() > 364) {

    $('.search-Bar').addClass('searchBarToTup')
  } else {
    $('.search-Bar').removeClass('searchBarToTup')
  }
});

/* Search Bar End-----------------------------------------------------*/
