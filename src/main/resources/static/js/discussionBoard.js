/* NavBar -------------------------------------------------------------------------> */

$(document).nvibarTop(function() {
    $('#navbarDropdown').mouseenter(function() {
      $('.dropdown-menu').slideToggle(300, "linear");
    });
    
    $('.dropdown-menu').mouseleave(function() {
      $(this).slideToggle(300, "linear");
    });
  });


// NavBarShadow

$(window).scroll(function() {
  if ($(window).scrollTop() > 10) {
      $('#navBar').addClass('floatingNav');
  } else {
      $('#navBar').removeClass('floatingNav');
  }
});

/* NavBar End-------------------------------------------------------------------------> */


/* Banner ----------------------------------------------------------------------------> */

$('#imageStellar').stellar();



$(document).ready(function(){
    $(".sidebar-btn").click(function(){
        $(".wrapper").toggleClass("collapse");
    });
});