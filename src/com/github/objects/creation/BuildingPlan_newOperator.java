package com.github.objects.creation;

/**
 * Creating Object in 5 ways.
 * 1. new Operator - directly purchasing the plan from Planner.
 * 
 * Reflection API « http://docs.oracle.com/javase/tutorial/reflect/member/ctorInstance.html
 * 2. Reflection API - Spring bean creation with default constructor
 * new Instance [default constructor] - purchasing from third person.
 * 3. Reflection API - Spring bean creation with parameterized constructor
 * new Instance [default constructor] - purchasing the modified plan from third person.
 * 
 * http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#clone--
 * 4. Clone method - reusing the plan in different place by the same person.
 * 5. Serialization and deSerialization of object. - Passing the object over network
 * and converting the stream to object back in another location.
 * 
 * https://stackoverflow.com/questions/95419/what-are-all-the-different-ways-to-create-an-object-in-java
 * https://stackoverflow.com/questions/38011767/how-many-ways-to-create-object-in-java
 * 
 * @author yashwanth.m
 *
 */
public class BuildingPlan_newOperator {

	public static void main(String[] args) {

	}

}
