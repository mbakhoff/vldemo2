$(function() {

    console.log("running demoapp.js");

    function loadArticles(list, articles) {
        console.log(articles);
    }

    $.ajax('/newspaper', {
        dataType: 'json',
        success: function(articlesJson) {
            console.info('ajax success');
            var articlesList = $('articles');
            loadArticles(articlesList, articlesJson);
        },
        error: function(req, text, error) {
            console.error('ajax failed');
        }
    });

});
