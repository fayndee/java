package com.structurizr.view;

import com.structurizr.AbstractWorkspaceTestBase;
import com.structurizr.Workspace;
import com.structurizr.model.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ViewTests extends AbstractWorkspaceTestBase {

    @Test
    public void test_description() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        StaticView view = new SystemContextView(softwareSystem);
        view.setDescription("Some description");
        assertEquals("Some description", view.getDescription());
    }

    @Test
    public void test_addAllSoftwareSystems_DoesNothing_WhenThereAreNoOtherSoftwareSystemsInTheModel() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        StaticView view = new SystemContextView(softwareSystem);
        assertEquals(1, view.getElements().size());
        view.addAllSoftwareSystems();
        assertEquals(1, view.getElements().size());
    }

    @Test
    public void test_addAllSoftwareSystems_DoesAddAllSoftwareSystems_WhenThereAreSoftwareSystemsInTheModel() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        SoftwareSystem softwareSystemA = model.addSoftwareSystem(Location.Unspecified, "System A", "Description");
        SoftwareSystem softwareSystemB = model.addSoftwareSystem(Location.Unspecified, "System B", "Description");
        SoftwareSystem softwareSystemC = model.addSoftwareSystem(Location.Unspecified, "System C", "Description");

        StaticView view = new SystemContextView(softwareSystem);
        view.addAllSoftwareSystems();

        assertEquals(4, view.getElements().size());
        Iterator<ElementView> it = view.getElements().iterator();
        assertSame(softwareSystem, it.next().getElement());
        assertSame(softwareSystemA, it.next().getElement());
        assertSame(softwareSystemB, it.next().getElement());
        assertSame(softwareSystemC, it.next().getElement());
    }

    @Test
    public void test_addSoftwareSystem_DoesNothing_WhenGivenNull() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        StaticView view = new SystemContextView(softwareSystem);
        view.add((SoftwareSystem)null);
        assertEquals(1, view.getElements().size());
    }

    @Test
    public void test_addSoftwareSystem_DoesNothing_WhenTheSoftwareSystemIsNotInTheModel() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        StaticView view = new SystemContextView(softwareSystem);

        Model model2 = new Model();
        SoftwareSystem softwareSystemA = model2.addSoftwareSystem(Location.Unspecified, "System A", "Description");
        view.add(softwareSystemA);
        assertEquals(1, view.getElements().size());
    }

    @Test
    public void test_addSoftwareSystem_AddsTheSoftwareSystem_WhenTheSoftwareSystemIsInTheModel() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        SoftwareSystem softwareSystemA = model.addSoftwareSystem(Location.Unspecified, "System A", "Description");

        StaticView view = new SystemContextView(softwareSystem);
        view.add(softwareSystemA);
        assertEquals(2, view.getElements().size());
        Iterator<ElementView> it = view.getElements().iterator();
        assertSame(softwareSystem, it.next().getElement());
        assertSame(softwareSystemA, it.next().getElement());
    }

    @Test
    public void test_addAllPeople_DoesNothing_WhenThereAreNoPeopleInTheModel() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");

        StaticView view = new SystemContextView(softwareSystem);
        assertEquals(1, view.getElements().size());

        view.addAllPeople();
        assertEquals(1, view.getElements().size());
    }

    @Test
    public void test_addAllPeople_DoesAddAllPeople_WhenThereArePeopleInTheModel() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        Person person1 = model.addPerson(Location.Unspecified, "Person 1", "Description");
        Person person2 = model.addPerson(Location.Unspecified, "Person 2", "Description");
        Person person3 = model.addPerson(Location.Unspecified, "Person 3", "Description");

        StaticView view = new SystemContextView(softwareSystem);
        view.addAllPeople();

        assertEquals(4, view.getElements().size());
        Iterator<ElementView> it = view.getElements().iterator();
        assertSame(softwareSystem, it.next().getElement());
        assertSame(person1, it.next().getElement());
        assertSame(person2, it.next().getElement());
        assertSame(person3, it.next().getElement());
    }

    @Test
    public void test_addPerson_DoesNothing_WhenGivenNull() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        StaticView view = new SystemContextView(softwareSystem);
        view.add((Person)null);
        assertEquals(1, view.getElements().size());
    }

    @Test
    public void test_addPerson_DoesNothing_WhenThePersonIsNotInTheModel() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        StaticView view = new SystemContextView(softwareSystem);

        Model model2 = new Model();
        Person person1 = model2.addPerson(Location.Unspecified, "Person 1", "Description");
        view.add(person1);
        assertEquals(1, view.getElements().size());
    }

    @Test
    public void test_addPerson_AddsThePerson_WhenThPersonIsInTheModel() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        StaticView view = new SystemContextView(softwareSystem);

        Person person1 = model.addPerson(Location.Unspecified, "Person 1", "Description");
        view.add(person1);

        assertEquals(2, view.getElements().size());
        Iterator<ElementView> it = view.getElements().iterator();
        assertSame(softwareSystem, it.next().getElement());
        assertSame(person1, it.next().getElement());
    }

    @Test
    public void test_removeElementsWithNoRelationships_RemovesAllElements_WhenTheViewHasNoRelationshipsBetweenElements() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        SoftwareSystem softwareSystemA = model.addSoftwareSystem(Location.Unspecified, "System A", "Description");
        Person person1 = model.addPerson(Location.Unspecified, "Person 1", "Description");

        StaticView view = new SystemContextView(softwareSystem);
        view.addAllSoftwareSystems();
        view.addAllPeople();
        view.removeElementsWithNoRelationships();

        assertEquals(0, view.getElements().size());
    }

    @Test
    public void test_removeElementsWithNoRelationships_RemovesOnlyThoseElementsWithoutRelationships_WhenTheViewContainsSomeUnlinkedElements() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        SoftwareSystem softwareSystemA = model.addSoftwareSystem(Location.Unspecified, "System A", "Description");
        SoftwareSystem softwareSystemB = model.addSoftwareSystem(Location.Unspecified, "System B", "Description");
        Person person1 = model.addPerson(Location.Unspecified, "Person 1", "Description");
        Person person2 = model.addPerson(Location.Unspecified, "Person 2", "Description");
        StaticView view = new SystemContextView(softwareSystem);

        softwareSystem.uses(softwareSystemA, "uses");
        person1.uses(softwareSystem, "uses");

        view.addAllSoftwareSystems();
        view.addAllPeople();
        assertEquals(5, view.getElements().size());

        view.removeElementsWithNoRelationships();
        assertEquals(3, view.getElements().size());
    }

    @Test
    public void test_copyLayoutInformationFrom() {
        Workspace workspace1 = new Workspace("", "");
        Model model1 = workspace1.getModel();
        SoftwareSystem softwareSystem1A = model1.addSoftwareSystem("System A", "Description");
        SoftwareSystem softwareSystem1B = model1.addSoftwareSystem("System B", "Description");
        Person person1 = model1.addPerson("Person", "Description");
        Relationship personUsesSoftwareSystem1 = person1.uses(softwareSystem1A, "Uses");

        // create a view with SystemA and Person (locations are set for both, relationship has vertices)
        StaticView view1 = new SystemContextView(softwareSystem1A);
        view1.add(softwareSystem1B);
        view1.getElementView(softwareSystem1B).setX(123);
        view1.getElementView(softwareSystem1B).setY(321);
        view1.add(person1);
        view1.getElementView(person1).setX(456);
        view1.getElementView(person1).setY(654);
        view1.getRelationshipView(personUsesSoftwareSystem1).setVertices(Arrays.asList(new Vertex(123, 456)));

        Workspace workspace2 = new Workspace("", "");
        Model model2 = workspace2.getModel();
        // creating these in the opposite order will cause them to get different internal IDs
        SoftwareSystem softwareSystem2B = model2.addSoftwareSystem("System B", "Description");
        SoftwareSystem softwareSystem2A = model2.addSoftwareSystem("System A", "Description");
        Person person2 = model2.addPerson("Person", "Description");
        Relationship personUsesSoftwareSystem2 = person2.uses(softwareSystem2A, "Uses");

        // create a view with SystemB and Person (locations are 0,0 for both)
        StaticView view2 = new SystemContextView(softwareSystem2A);
        view2.add(softwareSystem2B);
        view2.add(person2);
        assertEquals(0, view2.getElementView(softwareSystem2B).getX());
        assertEquals(0, view2.getElementView(softwareSystem2B).getY());
        assertEquals(0, view2.getElementView(softwareSystem2B).getX());
        assertEquals(0, view2.getElementView(softwareSystem2B).getY());
        assertEquals(0, view2.getElementView(person2).getX());
        assertEquals(0, view2.getElementView(person2).getY());
        assertTrue(view2.getRelationshipView(personUsesSoftwareSystem2).getVertices().isEmpty());

        view2.copyLayoutInformationFrom(view1);
        assertEquals(0, view2.getElementView(softwareSystem2A).getX());
        assertEquals(0, view2.getElementView(softwareSystem2A).getY());
        assertEquals(123, view2.getElementView(softwareSystem2B).getX());
        assertEquals(321, view2.getElementView(softwareSystem2B).getY());
        assertEquals(456, view2.getElementView(person2).getX());
        assertEquals(654, view2.getElementView(person2).getY());
        Vertex vertex = view2.getRelationshipView(personUsesSoftwareSystem2).getVertices().iterator().next();
        assertEquals(123, vertex.getX());
        assertEquals(456, vertex.getY());
    }

    @Test
    public void test_getTitle() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        SystemContextView systemContextView = new SystemContextView(softwareSystem);
        assertEquals("The System - System Context", systemContextView.getTitle());

        systemContextView.setDescription(null);
        assertEquals("The System - System Context", systemContextView.getTitle());

        systemContextView.setDescription("");
        assertEquals("The System - System Context", systemContextView.getTitle());

        systemContextView.setDescription("   ");
        assertEquals("The System - System Context", systemContextView.getTitle());

        systemContextView.setDescription("Description");
        assertEquals("The System - System Context - Description", systemContextView.getTitle());

        // and subtitle overrides description
        systemContextView.setSubtitle("Subtitle");
        assertEquals("The System - System Context - Subtitle", systemContextView.getTitle());
    }

    @Test
    public void test_removeElementsThatCantBeReachedFrom_DoesNothing_WhenANullElementIsSpecified() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        StaticView view = new SystemContextView(softwareSystem);
        view.removeElementsThatCantBeReachedFrom(null);
    }

    @Test
    public void test_removeElementsThatCantBeReachedFrom_DoesNothing_WhenAllElementsCanBeReached() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem("The System", "Description");
        SoftwareSystem softwareSystemA = model.addSoftwareSystem("System A", "");
        SoftwareSystem softwareSystemB = model.addSoftwareSystem("System B", "");

        softwareSystem.uses(softwareSystemA, "uses");
        softwareSystemA.uses(softwareSystemB, "uses");

        StaticView view = new SystemContextView(softwareSystem);
        view.addAllElements();
        assertEquals(3, view.getElements().size());

        view.removeElementsThatCantBeReachedFrom(softwareSystem);
        assertEquals(3, view.getElements().size());
    }

    @Test
    public void test_removeElementsThatCantBeReachedFrom_RemovesOrphanedElements_WhenThereAreSomeOrphanedElements() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem("The System", "Description");
        SoftwareSystem softwareSystemA = model.addSoftwareSystem("System A", "");
        SoftwareSystem softwareSystemB = model.addSoftwareSystem("System B", "");
        SoftwareSystem softwareSystemC = model.addSoftwareSystem("System C", "");

        softwareSystem.uses(softwareSystemA, "uses");
        softwareSystemA.uses(softwareSystemB, "uses");

        StaticView view = new SystemContextView(softwareSystem);
        view.addAllElements();
        assertEquals(4, view.getElements().size());

        view.removeElementsThatCantBeReachedFrom(softwareSystem);
        assertEquals(3, view.getElements().size());
        assertFalse(view.getElements().contains(new ElementView(softwareSystemC)));
    }

    @Test
    public void test_removeElementsThatCantBeReachedFrom_RemovesUnreachableElements_WhenThereAreSomeUnreachableElements() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem("The System", "Description");
        SoftwareSystem softwareSystemA = model.addSoftwareSystem("System A", "");
        SoftwareSystem softwareSystemB = model.addSoftwareSystem("System B", "");

        softwareSystem.uses(softwareSystemA, "uses");
        softwareSystemA.uses(softwareSystemB, "uses");

        StaticView view = new SystemContextView(softwareSystem);
        view.addAllElements();
        assertEquals(3, view.getElements().size());

        view.removeElementsThatCantBeReachedFrom(softwareSystemA);
        assertEquals(2, view.getElements().size());
        assertFalse(view.getElements().contains(new ElementView(softwareSystem)));
    }

    @Test
    public void test_removeElementsThatCantBeReachedFrom_DoesntIncludeAllElements_WhenThereIsACyclicGraph() {
        SoftwareSystem softwareSystem1 = model.addSoftwareSystem("Software System 1", "Description");
        SoftwareSystem softwareSystem2 = model.addSoftwareSystem("Software System 2", "Description");
        Person user = model.addPerson("User", "");

        user.uses(softwareSystem1, "");
        user.uses(softwareSystem2, "");
        softwareSystem1.delivers(user, "");

        StaticView view = new SystemContextView(softwareSystem1, "");
        view.addAllElements();
        assertEquals(3, view.getElements().size());

        // this should remove software system 2
        view.removeElementsThatCantBeReachedFrom(softwareSystem1);
        assertEquals(2, view.getElements().size());
        assertTrue(view.getElements().contains(new ElementView(softwareSystem1)));
        assertTrue(view.getElements().contains(new ElementView(user)));
    }

    @Test
    public void test_removeRelationship_DoesNothing_WhenNullIsSpecified() {
        SoftwareSystem softwareSystem1 = model.addSoftwareSystem("Software System 1", "Description");
        SoftwareSystem softwareSystem2 = model.addSoftwareSystem("Software System 2", "Description");
        SoftwareSystem softwareSystem3 = model.addSoftwareSystem("Software System 3", "Description");

        softwareSystem1.uses(softwareSystem2, "Uses");
        softwareSystem2.uses(softwareSystem3, "Uses");
        softwareSystem3.uses(softwareSystem1, "Uses");

        StaticView view = new SystemContextView(softwareSystem1, "");
        view.addAllElements();

        assertEquals(3, view.getRelationships().size());
        view.remove((Relationship)null);
    }

    @Test
    public void test_removeRelationship_RemovesARelationship_WhenAValidRelationshipIsSpecified() {
        SoftwareSystem softwareSystem1 = model.addSoftwareSystem("Software System 1", "Description");
        SoftwareSystem softwareSystem2 = model.addSoftwareSystem("Software System 2", "Description");
        SoftwareSystem softwareSystem3 = model.addSoftwareSystem("Software System 3", "Description");

        Relationship relationship12 = softwareSystem1.uses(softwareSystem2, "Uses");
        Relationship relationship23 = softwareSystem2.uses(softwareSystem3, "Uses");
        Relationship relationship31 = softwareSystem3.uses(softwareSystem1, "Uses");

        StaticView view = new SystemContextView(softwareSystem1, "");
        view.addAllElements();

        assertEquals(3, view.getRelationships().size());
        view.remove(relationship31);

        assertEquals(2, view.getRelationships().size());
        assertTrue(view.getRelationships().contains(new RelationshipView(relationship12)));
        assertTrue(view.getRelationships().contains(new RelationshipView(relationship23)));
    }

}
