/**
 * Use Parse.Cloud.define to define as many cloud functions as you want.
 */
Parse.Cloud.define("hello", function(request, response) {
	response.success("Hello world!");
});

/**
 * Find a merge in History Fights with a fighter such that both request fighters
 * have fought before.
 * 
 * For example: fighter vs. X and opponent vs. X
 */
Parse.Cloud.define("matchesHistory", function(request, response) {
	var OPPONENT_FIELD = "opponent";
	var fighter = request.params.fighter;
	var opponent = request.params.opponent;
	var fighterRecs = getFightHistory(fighter);
	var opponentRecs = getFightHistory(opponent);
	var interRecs = new Array();
	var fRec, oRec;
	for (fRec in fighterRecs)
		for (oRec in opponentRecs)
			if (fRec.get(OPPONENT_FIELD) == oRec.get(OPPONENT_FIELD)) {
				interRecs.push(fRec.get(OPPONENT_FIELD));
				response.success(fRec.get(OPPONENT_FIELD));
			}
	response.success(fighterRecs);
});

function getFightHistory(fighterId) {
	var query = new Parse.Query("FightHistory");
	query.equalTo("fighter", fighterId);
	query.find({
		success : function(records) {
			return recs;
		},
		error : function() {
			return null;
		}
	});
}