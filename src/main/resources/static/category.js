'use strict'
$(function() {
	$('#largeCategory').change(function() {
		let hostUrl = 'http://localhost:8080/itemadministration/showMediumCategory';
		$.ajax({
			url: hostUrl,
			type: 'POST',
			dataType: 'json',
			data: {
				parent: $('#largeCategory').val()
			},
			async: true,

		}).done(function(mediumCategory) {
			$('#mediumCategory > option').remove();
			$('#mediumCategory').append(
					$('<option>').html('-- childCategory --'));
			for (let i = 0; i < mediumCategory.length; i++) {
				$('#mediumCategory').append(
					$('<option>').html(mediumCategory[i].name).val(mediumCategory[i].id));
				console.log(mediumCategory[i])

			}

		})
			.fail(function() {
			});
	});
	$('#mediumCategory').change(function() {
		let hostUrl = 'http://localhost:8080/itemadministration/showSmallCategory';
		$.ajax({
			url: hostUrl,
			type: 'POST',
			dataType: 'json',
			data: {
				parent: $('#mediumCategory').val()
			},
			async: true,

		}).done(function(smallCategory) {
			$('#smallCategory > option').remove();
			$('#smallCategory').append(
					$('<option>').html('-- grandChild --'));
			for (let i = 0; i < smallCategory.length; i++) {
				$('#smallCategory').append(
					$('<option>').html(smallCategory[i].name).val(smallCategory[i].val));
				console.log(smallCategory[i])

			}

		})
			.fail(function() {
			});
	});
	
	
	
});
