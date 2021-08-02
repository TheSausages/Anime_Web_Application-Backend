package pwr.pracainz.entities.anime.query.parameters.connections.staff;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StaffEdgeTest {

    @Test
    void StaffEdgeBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> StaffEdge.getStaffEdgeBuilder().buildStaffEdge());

        //then
        assertEquals(exception.getMessage(), "Staff Edge should posses at least 1 parameter!");
    }

    @Test
    void StaffEdgeBuilder_Node_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .node(staff)
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeString(), "staffEdge {\nnode {\nid\n}\n}");
    }

    @Test
    void StaffEdgeBuilder_ManyNode_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .age()
                .buildStaff();
        Staff staff1 = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .node(staff)
                .node(staff1)
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeString(), "staffEdge {\nnode {\nage\n}\n}");
    }

    @Test
    void StaffEdgeBuilder_NodeWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .age()
                .buildStaff();

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .node(staff)
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeWithoutFieldName(), "{\nnode {\nage\n}\n}");
    }

    @Test
    void StaffEdgeBuilder_ManyNodeWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .age()
                .buildStaff();
        Staff staff1 = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .node(staff)
                .node(staff1)
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeWithoutFieldName(), "{\nnode {\nage\n}\n}");
    }

    @Test
    void StaffEdgeBuilder_Id_NoException() {
        //given

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .id()
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeString(), "staffEdge {\nid\n}");
    }

    @Test
    void StaffEdgeBuilder_ManyId_NoException() {
        //given

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .id()
                .id()
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeString(), "staffEdge {\nid\n}");
    }

    @Test
    void StaffEdgeBuilder_IdWithoutFieldName_NoException() {
        //given

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .id()
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeWithoutFieldName(), "{\nid\n}");
    }

    @Test
    void StaffEdgeBuilder_ManyIdWithoutFieldName_NoException() {
        //given

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .id()
                .id()
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeWithoutFieldName(), "{\nid\n}");
    }

    @Test
    void StaffEdgeBuilder_Role_NoException() {
        //given

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .role()
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeString(), "staffEdge {\nrole\n}");
    }

    @Test
    void StaffEdgeBuilder_ManyRole_NoException() {
        //given

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .role()
                .role()
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeString(), "staffEdge {\nrole\n}");
    }

    @Test
    void StaffEdgeBuilder_RoleWithoutFieldMane_NoException() {
        //given

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .role()
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeWithoutFieldName(), "{\nrole\n}");
    }

    @Test
    void StaffEdgeBuilder_ManyRoleWithoutFieldMane_NoException() {
        //given

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .role()
                .role()
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeWithoutFieldName(), "{\nrole\n}");
    }

    @Test
    void StaffEdgeBuilder_FavouriteOrder_NoException() {
        //given

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .favouriteOrder()
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeString(), "staffEdge {\nfavouriteOrder\n}");
    }

    @Test
    void StaffEdgeBuilder_ManyFavouriteOrder_NoException() {
        //given

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .favouriteOrder()
                .favouriteOrder()
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeString(), "staffEdge {\nfavouriteOrder\n}");
    }

    @Test
    void StaffEdgeBuilder_FavouriteOrderWithoutFieldName_NoException() {
        //given

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .favouriteOrder()
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeWithoutFieldName(), "{\nfavouriteOrder\n}");
    }

    @Test
    void StaffEdgeBuilder_ManyFavouriteOrderWithoutFieldName_NoException() {
        //given

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .favouriteOrder()
                .favouriteOrder()
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeWithoutFieldName(), "{\nfavouriteOrder\n}");
    }

    @Test
    void StaffEdgeBuilder_NodeAndManyRoleWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .image()
                .buildStaff();

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .role()
                .node(staff)
                .role()
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeWithoutFieldName(), "{\nrole\nnode {\nimage {\nlarge\nmedium\n}\n}\n}");
    }

    @Test
    void StaffEdgeBuilder_AllAndManyFavouriteOrder_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .image()
                .buildStaff();

        //when
        StaffEdge edge = StaffEdge.getStaffEdgeBuilder()
                .role()
                .favouriteOrder()
                .node(staff)
                .id()
                .favouriteOrder()
                .favouriteOrder()
                .buildStaffEdge();

        //then
        assertEquals(edge.getStaffEdgeString(), "staffEdge {\nrole\nfavouriteOrder\nnode {\nimage {\nlarge\nmedium\n}\n}\nid\n}");
    }
}