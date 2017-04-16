jQuery("#leftside-navigation .sub-menu > a").click(function(e) {
  jQuery("#leftside-navigation ul ul").slideUp(), jQuery(this).next().is(":visible") || jQuery(this).next().slideDown(),
  e.stopPropagation()
})