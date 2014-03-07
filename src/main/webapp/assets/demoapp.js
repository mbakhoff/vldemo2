$(function() {

    console.log("running demoapp.js");

    function loadArticles(list, articles) {
        // http://api.jquery.com/jQuery.each/
        $.each(articles, function(i, article) {
            var newListItem = $('<li/>');
            newListItem.text(article.title);
            list.append(newListItem);
        });
    }

    // http://api.jquery.com/jQuery.ajax/
    $.ajax('/newspaper', {
        dataType: 'json',
        success: function(articlesJson) {
            console.info('ajax success');
            var articlesList = $('#articles');
            loadArticles(articlesList, articlesJson);
        },
        error: function(req, text, error) {
            console.error('ajax failed');
        }
    });

});
