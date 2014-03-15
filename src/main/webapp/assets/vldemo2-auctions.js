
// RTFM:
// http://javascript.crockford.com/private.html
// http://blog.stannard.net.au/2011/01/14/creating-namespaces-in-javascript/

var auctions = auctions || {};

auctions.BrowsingList = function(jList, viewingList) {
    this.list = jList;
    this.view = viewingList;
};

auctions.BrowsingList.prototype.addItem = function(item) {
    var self = this;

    var newListItem = $('<li/>');
    newListItem.text(item.title);
    newListItem.click(function() {
        // 'this' is the item that was clicked
        // 'self' is the BrowsingList object
        self.view.setViewContent(item);
    });
    this.list.append(newListItem);
};



auctions.ViewingList = function(jViewPanel) {
    this.view = jViewPanel;
    this.descriptionParagraph = $('#itemdescription', jViewPanel);
    this.bidsDiv = $('#bids', jViewPanel);
    this.selectedItem = null;
};

auctions.ViewingList.prototype.setViewContent = function(itemToDisplay) {
    this.descriptionParagraph.html(
        itemToDisplay.title + ": <br/>" + itemToDisplay.description);

    this.clearBids();
    for (var i = 0; i < itemToDisplay.bids.length; i++) {
        this.addBid(itemToDisplay.bids[i]);
    }

    this.view.removeClass('hidden');
    this.selectedItem = itemToDisplay;
};

auctions.ViewingList.prototype.clearBids = function() {
    this.bidsDiv.empty();
};

auctions.ViewingList.prototype.addBid = function(bid) {
    var bidElement = $('<div/>');
    bidElement.text(bid.bidder + " bidded " + bid.amount);
    bidElement.hide();
    this.bidsDiv.append(bidElement);
    bidElement.fadeToggle();
};



auctions.BidBuilder = function(jInputSection) {
    this.bidderBox = $('#bidder', jInputSection);
    this.amountBox = $('#amount', jInputSection);
};

auctions.BidBuilder.prototype.clear = function() {
    this.bidderBox.val('');
    this.amountBox.val('');
};

auctions.BidBuilder.prototype.buildBid = function(item) {
    return {
        itemId: item.id,
        bidder: this.bidderBox.val(),
        amount: this.amountBox.val()
    };
};
