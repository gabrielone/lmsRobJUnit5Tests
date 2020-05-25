	$('#idTitle').summernote(
			{
				placeholder : 'Title',
				toolbar : [ [ 'style', [ 'style' ] ],
					[ 'font', [ 'bold', 'underline', 'clear' ] ],
					[ 'color', [ 'color' ] ] ]
			});
	$('#idImage').summernote({
		placeholder : 'Load an image',
		toolbar : [ [ 'insert', [ 'picture' ] ] ]
	});
	$('#idContent').summernote({
		placeholder : 'Write some more content',
		tabsize : 2,
		height : 300
	});

