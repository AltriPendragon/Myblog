function change() {
    $(this).siblings('.left-nav-sub-menu').slideToggle(80).end().find('.left-nav-icon').toggleClass('left-nav-icon-rotate');
}

$(document).on("click",".nav-link-toggle",function () {
    $(this).siblings('.left-nav-sub-menu').slideToggle(80).end().find('.left-nav-icon').toggleClass('left-nav-icon-rotate');
})
