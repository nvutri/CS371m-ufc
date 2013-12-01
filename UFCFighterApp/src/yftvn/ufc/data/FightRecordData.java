package yftvn.ufc.data;

import java.util.ArrayList;
import java.util.List;

import yftvn.ufc.models.FightRecord;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class FightRecordData {
	private static final String FIGHT_RECORD_TABLE = "FightHistory";
	private static final String FIGHTER_FIELD = "fighter";
	private static final String OPPONENT_FIELD = "opponent";
	private static final String EVENT_ID_FIELD = "eventId";
	private static final String RESULT_FIELD = "result";
	private static final String DECISION_FIELD = "decision";
	private static final String ROUND_FIELD = "round";
	private static final String TIME_FIELD = "time";

	/**
	 * Get a list of fight history record given the fighter Id.
	 * 
	 * @param fighterId
	 */
	public static ArrayList<FightRecord> getSingleFightRecords(Integer fighterId) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(FIGHT_RECORD_TABLE);
		query.whereEqualTo(FIGHTER_FIELD, fighterId);
		// Query the find event;
		List<ParseObject> parseFightRecordList = new ArrayList<ParseObject>();
		try {
			parseFightRecordList = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return adaptFightRecord(parseFightRecordList);
	}

	/**
	 * Get a list of previous FightRecords btw 2 fighters. This is done by
	 * filtering out the FightHistory of one fighter with the opponentId.
	 * 
	 * @param fighterId
	 * @param opponentId
	 */
	public static ArrayList<FightRecord> getPrevFightRecords(Integer fighterId,
			Integer opponentId) {
		ArrayList<FightRecord> allSingleFights = getSingleFightRecords(fighterId);
		ArrayList<FightRecord> prevFights = new ArrayList<FightRecord>();
		for (FightRecord fRec : allSingleFights)
			if (fRec.getOpponent().equals(opponentId)) {
				prevFights.add(fRec);
			}
		return prevFights;
	}

	/**
	 * Get an intersection of previously fought opponents of 2 fighters.
	 * 
	 * @param fighterOne
	 * @param fighterTwo
	 * @return an ArrayList of comparing fights. FighterOne and then FighterTwo
	 *         record in pair.
	 */
	public static ArrayList<FightRecord> getComparisonFight(Integer fighterOne,
			Integer fighterTwo) {
		ArrayList<FightRecord> oneRecs = getSingleFightRecords(fighterOne);
		ArrayList<FightRecord> twoRecs = getSingleFightRecords(fighterTwo);
		ArrayList<FightRecord> interRecs = new ArrayList<FightRecord>();
		for (FightRecord oneRec : oneRecs)
			for (FightRecord twoRec : twoRecs)
				if (oneRec.getOpponent().equals(twoRec.getOpponent())) {
					interRecs.add(oneRec);
					interRecs.add(twoRec);
				}
		return interRecs;
	}

	/**
	 * Adapt the list of Parse Object into a list of FightRecord
	 * 
	 * @param parseFightRecordList
	 * @return adapted ArrayList of FightRecord.
	 */
	private static ArrayList<FightRecord> adaptFightRecord(
			List<ParseObject> parseFightRecordList) {
		ArrayList<FightRecord> fightRecords = new ArrayList<FightRecord>();
		for (ParseObject parseFRec : parseFightRecordList) {
			// FightRecord(eventId, fighter, opponent, round, time, result,
			// decision)
			FightRecord fRec = new FightRecord(
					parseFRec.getInt(EVENT_ID_FIELD),
					parseFRec.getInt(FIGHTER_FIELD),
					parseFRec.getInt(OPPONENT_FIELD),
					parseFRec.getString(ROUND_FIELD),
					parseFRec.getString(TIME_FIELD),
					parseFRec.getString(RESULT_FIELD),
					parseFRec.getString(DECISION_FIELD));
			fightRecords.add(fRec);
		}
		return fightRecords;
	}
}
