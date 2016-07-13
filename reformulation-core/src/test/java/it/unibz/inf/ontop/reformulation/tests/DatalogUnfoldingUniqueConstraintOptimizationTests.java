package it.unibz.inf.ontop.reformulation.tests;

/*
 * #%L
 * ontop-reformulation-core
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

import it.unibz.inf.ontop.model.CQIE;
import it.unibz.inf.ontop.model.DatalogProgram;
import it.unibz.inf.ontop.model.Function;
import it.unibz.inf.ontop.model.OBDADataFactory;
import it.unibz.inf.ontop.model.Predicate;
import it.unibz.inf.ontop.model.Term;
import it.unibz.inf.ontop.model.impl.OBDADataFactoryImpl;
import it.unibz.inf.ontop.owlrefplatform.core.basicoperations.DBMetadataUtil;
import it.unibz.inf.ontop.owlrefplatform.core.unfolding.DatalogUnfolder;
import it.unibz.inf.ontop.sql.DBMetadata;
import it.unibz.inf.ontop.sql.DBMetadataExtractor;
import it.unibz.inf.ontop.sql.QuotedIDFactory;
import it.unibz.inf.ontop.sql.DatabaseRelationDefinition;
import it.unibz.inf.ontop.sql.UniqueConstraint;
import junit.framework.TestCase;

import java.sql.Types;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Multimap;

public class DatalogUnfoldingUniqueConstraintOptimizationTests extends TestCase {

	DBMetadata metadata;

	OBDADataFactory fac = OBDADataFactoryImpl.getInstance();

	DatalogProgram unfoldingProgram;

	@Override
	public void setUp() {
		metadata = DBMetadataExtractor.createDummyMetadata();
		QuotedIDFactory idfac = metadata.getQuotedIDFactory();
		
		DatabaseRelationDefinition table = metadata.createDatabaseRelation(idfac.createRelationID(null, "TABLE"));
		table.addAttribute(idfac.createAttributeID("col1"), Types.INTEGER, null, true);
		table.addAttribute(idfac.createAttributeID("col2"), Types.INTEGER, null, true);
		table.addAttribute(idfac.createAttributeID("col3"), Types.INTEGER, null, true);
		table.addAttribute(idfac.createAttributeID("col4"), Types.INTEGER, null, true);
		table.addUniqueConstraint(UniqueConstraint.primaryKeyOf(table.getAttribute(idfac.createAttributeID("col4"))));
		
		table = metadata.createDatabaseRelation(idfac.createRelationID(null, "TABLE2"));
		table.addAttribute(idfac.createAttributeID("col1"), Types.INTEGER, null, false);
		table.addAttribute(idfac.createAttributeID("col2"), Types.INTEGER, null, false);
		table.addAttribute(idfac.createAttributeID("col3"), Types.INTEGER, null, false);
		table.addAttribute(idfac.createAttributeID("col4"), Types.INTEGER, null, false);
		table.addUniqueConstraint(UniqueConstraint.primaryKeyOf(table.getAttribute(idfac.createAttributeID("col1"))));

        unfoldingProgram = fac.getDatalogProgram();

        // name(x,y) :- TABLE(x,y,z,m)
		Function head = fac.getFunction(fac.getDataPropertyPredicate("name"), fac.getVariable("x"), fac.getVariable("y"));
		List<Term> bodyTerms = new LinkedList<Term>();
		bodyTerms.add(fac.getVariable("x"));
		bodyTerms.add(fac.getVariable("y"));
		bodyTerms.add(fac.getVariable("z"));
		bodyTerms.add(fac.getVariable("m"));
		Function body = fac.getFunction(fac.getPredicate("TABLE", 4), bodyTerms);
		CQIE rule = fac.getCQIE(head, body);
		unfoldingProgram.appendRule(rule);

        // lastname(x,z) :- TABLE(x,y,z,m)
		head = fac.getFunction(fac.getDataPropertyPredicate("lastname"), fac.getVariable("x"), fac.getVariable("z"));
		bodyTerms = new LinkedList<Term>();
		bodyTerms.add(fac.getVariable("x"));
		bodyTerms.add(fac.getVariable("y"));
		bodyTerms.add(fac.getVariable("z"));
		bodyTerms.add(fac.getVariable("m"));
		body = fac.getFunction(fac.getPredicate("TABLE", 4), bodyTerms);
		rule = fac.getCQIE(head, body);
		unfoldingProgram.appendRule(rule);

        // id(x,m) :- TABLE(x,y,z,m)
		head = fac.getFunction(fac.getDataPropertyPredicate("id"), fac.getVariable("x"), fac.getVariable("m"));
		bodyTerms = new LinkedList<Term>();
		bodyTerms.add(fac.getVariable("x"));
		bodyTerms.add(fac.getVariable("y"));
		bodyTerms.add(fac.getVariable("z"));
		bodyTerms.add(fac.getVariable("m"));
		body = fac.getFunction(fac.getPredicate("TABLE", 4), bodyTerms);
		rule = fac.getCQIE(head, body);
		unfoldingProgram.appendRule(rule);

        // id1(y,m) :- TABLE(x,y,z,m)
        head = fac.getFunction(fac.getDataPropertyPredicate("id1"), fac.getVariable("y"), fac.getVariable("m"));
        bodyTerms = new LinkedList<Term>();
        bodyTerms.add(fac.getVariable("x"));
        bodyTerms.add(fac.getVariable("y"));
        bodyTerms.add(fac.getVariable("z"));
        bodyTerms.add(fac.getVariable("m"));
        body = fac.getFunction(fac.getPredicate("TABLE", 4), bodyTerms);
        rule = fac.getCQIE(head, body);
        unfoldingProgram.appendRule(rule);


//        // name(x,y) :- TABLE2(x,y,z,m)
//		head = fac.getFunction(fac.getDataPropertyPredicate("name"), fac.getVariable("x"), fac.getVariable("y"));
//		bodyTerms = new LinkedList<Term>();
//		bodyTerms.add(fac.getVariable("x"));
//		bodyTerms.add(fac.getVariable("y"));
//		bodyTerms.add(fac.getVariable("z"));
//		bodyTerms.add(fac.getVariable("m"));
//		body = fac.getFunction(fac.getPredicate("TABLE2", 4), bodyTerms);
//		rule = fac.getCQIE(head, body);
//		unfoldingProgram.appendRule(rule);
//
//        // lastname(x,z) :- TABLE2(x,y,z,m)
//        head = fac.getFunction(fac.getDataPropertyPredicate("lastname"), fac.getVariable("x"), fac.getVariable("z"));
//		bodyTerms = new LinkedList<Term>();
//		bodyTerms.add(fac.getVariable("x"));
//		bodyTerms.add(fac.getVariable("y"));
//		bodyTerms.add(fac.getVariable("z"));
//		bodyTerms.add(fac.getVariable("m"));
//		body = fac.getFunction(fac.getPredicate("TABLE2", 4), bodyTerms);
//		rule = fac.getCQIE(head, body);
//		unfoldingProgram.appendRule(rule);
//
//        // id(x,m) :- TABLE2(x,y,z,m)
//		head = fac.getFunction(fac.getDataPropertyPredicate("id"), fac.getVariable("x"), fac.getVariable("m"));
//		bodyTerms = new LinkedList<Term>();
//		bodyTerms.add(fac.getVariable("x"));
//		bodyTerms.add(fac.getVariable("y"));
//		bodyTerms.add(fac.getVariable("z"));
//		bodyTerms.add(fac.getVariable("m"));
//		body = fac.getFunction(fac.getPredicate("TABLE2", 4), bodyTerms);
//		rule = fac.getCQIE(head, body);
//		unfoldingProgram.appendRule(rule);
	}

	public void testRedundancyElimination() throws Exception {
		Multimap<Predicate, List<Integer>> pkeys = DBMetadataUtil.extractPKs(metadata);
		DatalogUnfolder unfolder = new DatalogUnfolder(unfoldingProgram.getRules(), pkeys);

        // q(m, n, p) :-  id(m, p), id1(n, p)
		LinkedList<Term> headterms = new LinkedList<Term>();
		headterms.add(fac.getVariable("m"));
		headterms.add(fac.getVariable("n"));
		headterms.add(fac.getVariable("p"));

		Function head = fac.getFunction(fac.getPredicate("q", 3), headterms);
		List<Function> body = new LinkedList<Function>();
		body.add(fac.getFunction(fac.getDataPropertyPredicate("id"), fac.getVariable("m"), fac.getVariable("p")));
        body.add(fac.getFunction(fac.getDataPropertyPredicate("id1"), fac.getVariable("n"), fac.getVariable("p")));
		CQIE query = fac.getCQIE(head, body);

		DatalogProgram input = fac.getDatalogProgram();
		input.appendRule(query);
		DatalogProgram output = unfolder.unfold(input);
		System.out.println("input " + input);

		int atomcount = 0;
		for (CQIE result: output.getRules()) {
			for (Function atom: result.getBody()) {
				atomcount +=1;
			}
		}
		
		System.out.println("output " + output);
		assertEquals(output.toString(), 1, atomcount);


//        // q(x, y, m, o) :- name(s1, x),  name(s2, m),  lastname(s1, y), lastname(s2, o)
//		headterms = new LinkedList<Term>();
//		headterms.add(fac.getVariable("x"));
//		headterms.add(fac.getVariable("y"));
//		headterms.add(fac.getVariable("m"));
//		headterms.add(fac.getVariable("o"));
//
//		head = fac.getFunction(fac.getPredicate("q", 4), headterms);
//		body = new LinkedList<Function>();
//		body.add(fac.getFunction(fac.getDataPropertyPredicate("name"), fac.getVariable("s1"), fac.getVariable("x")));
//		body.add(fac.getFunction(fac.getDataPropertyPredicate("name"), fac.getVariable("s2"), fac.getVariable("m")));
//		body.add(fac.getFunction(fac.getDataPropertyPredicate("lastname"), fac.getVariable("s1"), fac.getVariable("y")));
//		body.add(fac.getFunction(fac.getDataPropertyPredicate("lastname"), fac.getVariable("s2"), fac.getVariable("o")));
//		query = fac.getCQIE(head, body);
//
//		input = fac.getDatalogProgram(query);
//		output = unfolder.unfold(input, "q");
//		System.out.println("input " + input);
//
//		atomcount = 0;
//		for (CQIE result: output.getRules()) {
//			for (Function atom: result.getBody()) {
//				atomcount +=1;
//			}
//		}
//
//		System.out.println("output " + output);
//		assertTrue(output.toString(), atomcount == 48);

	}
}
