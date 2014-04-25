package de.thm.arsnova.connector.dao;

import java.util.List;
import java.util.Map;

import de.thm.arsnova.connector.model.IliasCategoryNode;
import de.thm.arsnova.connector.model.IliasQuestion;

public interface UniRepDao {
	/** Returns a flat dump of the Ilias repository tree.
	 *
	 * @param refId The root nodes ID as reference ID
	 * @return A list of category nodes
	 */
	List<IliasCategoryNode> getTreeObjects(int refId);

	/** Returns a list of questions identified by the parent question pool reference ID
	 *
	 * @param refId The reference id of the question pool containing this question
	 * @return A list of questions containing the question, possible answers and feedback.
	 */
	List<IliasQuestion> getQuestion(int refId);

	Map<String, String> getReferenceIdsWithMetaDataFlag(String metaDataTitle);
}
