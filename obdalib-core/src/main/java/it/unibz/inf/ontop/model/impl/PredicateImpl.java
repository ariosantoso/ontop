package it.unibz.inf.ontop.model.impl;

/*
 * #%L
 * ontop-obdalib-core
 * %%
 * Copyright (C) 2009 - 2014 Free University of Bozen-Bolzano
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import it.unibz.inf.ontop.model.*;

import static it.unibz.inf.ontop.model.impl.OBDAVocabulary.CANONICAL_IRI;
import static it.unibz.inf.ontop.model.impl.OBDAVocabulary.SAME_AS;

public class PredicateImpl implements Predicate {

	public static final Predicate QUEST_TRIPLE_PRED = new PredicateImpl("triple", 3, new COL_TYPE[3]);	
	
	private int arity = -1;
	private String name = null;
	private int identifier = -1;
	private COL_TYPE[] types = null;

	protected PredicateImpl(String name, int arity, COL_TYPE[] types) {
		this.name = name;
		this.identifier = name.hashCode();
		this.arity = arity;
		this.types = types;
	}

	@Override
	public int getArity() {
		return arity;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof PredicateImpl)) {
			return false;
		}
		PredicateImpl pred2 = (PredicateImpl) obj;
		return this.identifier == pred2.identifier;
	}

	@Override
	public int hashCode() {
		return identifier;
	}

	@Override
	public Predicate clone() {
		return this;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public COL_TYPE getType(int column) {
		if (types != null) {
			return types[column];
		}
		return null;
	}

	@Override
	public boolean isClass() {
		return (arity == 1 && getType(0) == COL_TYPE.OBJECT);
	}

	@Override
	public boolean isObjectProperty() {
		return (arity == 2 && getType(0) == COL_TYPE.OBJECT && getType(1) == COL_TYPE.OBJECT); 
	}

	@Override
	public boolean isAnnotationProperty() {
		return (arity == 2 && getType(0) == COL_TYPE.OBJECT && getType(1) == COL_TYPE.NULL);
	}

	@Override
	public boolean isDataProperty() {
		return (arity == 2 && getType(0) == COL_TYPE.OBJECT && getType(1) == COL_TYPE.LITERAL); 
	}

	@Override
	public boolean isSameAsProperty() {
		return (arity == 2 && name.equals(SAME_AS) && getType(0) == COL_TYPE.OBJECT && getType(1) == COL_TYPE.OBJECT);
	}

	@Override
	public boolean isCanonicalIRIProperty() {
		return (arity == 2 && name.equals(CANONICAL_IRI) && getType(0) == COL_TYPE.OBJECT && getType(1) == COL_TYPE.OBJECT);
	}


	@Override
	public boolean isTriplePredicate() {
		return (arity == 3 && name.equals(QUEST_TRIPLE_PRED.getName()));
	}

}
