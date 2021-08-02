package pwr.pracainz.entities.anime.query.parameters.connections.staff;

import org.junit.jupiter.api.Test;
import pwr.pracainz.entities.anime.query.parameters.connections.PageInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StaffConnectionTest {

    @Test
    void StaffConnectionBuilder_NoParam_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> StaffConnection.getMediaConnectionBuilder().buildStaffConnection());

        //then
        assertEquals(exception.getMessage(), "Staff Connection should posses at least 1 parameter!");
    }

    @Test
    void StaffConnectionBuilder_Edges_NoException() {
        //given
        StaffEdge staffEdge = StaffEdge.getStaffEdgeBuilder()
                .id()
                .buildStaffEdge();

        //when
        StaffConnection staffConnection = StaffConnection.getMediaConnectionBuilder()
                .edges(staffEdge)
                .buildStaffConnection();

        //then
        assertEquals(staffConnection.getStaffConnectionString(), "staffConnection {\nedges {\nid\n}\n}");
    }

    @Test
    void StaffConnectionBuilder_ManyEdges_NoException() {
        //given
        StaffEdge staffEdge1 = StaffEdge.getStaffEdgeBuilder()
                .id()
                .buildStaffEdge();
        StaffEdge staffEdge = StaffEdge.getStaffEdgeBuilder()
                .role()
                .buildStaffEdge();

        //when
        StaffConnection staffConnection = StaffConnection.getMediaConnectionBuilder()
                .edges(staffEdge)
                .edges(staffEdge1)
                .buildStaffConnection();

        //then
        assertEquals(staffConnection.getStaffConnectionString(), "staffConnection {\nedges {\nrole\n}\n}");
    }

    @Test
    void StaffConnectionBuilder_EdgesWithoutFieldName_NoException() {
        //given
        StaffEdge staffEdge = StaffEdge.getStaffEdgeBuilder()
                .id()
                .buildStaffEdge();

        //when
        StaffConnection staffConnection = StaffConnection.getMediaConnectionBuilder()
                .edges(staffEdge)
                .buildStaffConnection();

        //then
        assertEquals(staffConnection.getStaffConnectionWithoutFieldName(), "{\nedges {\nid\n}\n}");
    }

    @Test
    void StaffConnectionBuilder_ManyEdgesWithoutFieldName_NoException() {
        //given
        StaffEdge staffEdge1 = StaffEdge.getStaffEdgeBuilder()
                .id()
                .buildStaffEdge();
        StaffEdge staffEdge = StaffEdge.getStaffEdgeBuilder()
                .role()
                .buildStaffEdge();

        //when
        StaffConnection staffConnection = StaffConnection.getMediaConnectionBuilder()
                .edges(staffEdge)
                .edges(staffEdge1)
                .buildStaffConnection();

        //then
        assertEquals(staffConnection.getStaffConnectionWithoutFieldName(), "{\nedges {\nrole\n}\n}");
    }

    @Test
    void StaffConnectionBuilder_Nodes_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .age()
                .buildStaff();

        //when
        StaffConnection staffConnection = StaffConnection.getMediaConnectionBuilder()
                .nodes(staff)
                .buildStaffConnection();

        //then
        assertEquals(staffConnection.getStaffConnectionString(), "staffConnection {\nnodes {\nage\n}\n}");
    }

    @Test
    void StaffConnectionBuilder_ManyNodes_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .homeTown()
                .buildStaff();
        Staff staff1 = Staff.getStaffBuilder()
                .age()
                .buildStaff();

        //when
        StaffConnection staffConnection = StaffConnection.getMediaConnectionBuilder()
                .nodes(staff)
                .nodes(staff1)
                .buildStaffConnection();

        //then
        assertEquals(staffConnection.getStaffConnectionString(), "staffConnection {\nnodes {\nhomeTown\n}\n}");
    }

    @Test
    void StaffConnectionBuilder_PageInfo_NoException() {
        //given
        PageInfo pageInfo = PageInfo.getPageInfoBuilder()
                .total()
                .buildPageInfo();

        //when
        StaffConnection staffConnection = StaffConnection.getMediaConnectionBuilder()
                .pageInfo(pageInfo)
                .buildStaffConnection();

        //then
        assertEquals(staffConnection.getStaffConnectionString(), "staffConnection {\npageInfo {\ntotal\n}\n}");
    }

    @Test
    void StaffConnectionBuilder_ManyPageInfo_NoException() {
        //given
        PageInfo pageInfo = PageInfo.getPageInfoBuilder()
                .lastPage()
                .buildPageInfo();
        PageInfo pageInfo1 = PageInfo.getPageInfoBuilder()
                .total()
                .buildPageInfo();

        //when
        StaffConnection staffConnection = StaffConnection.getMediaConnectionBuilder()
                .pageInfo(pageInfo)
                .pageInfo(pageInfo1)
                .buildStaffConnection();

        //then
        assertEquals(staffConnection.getStaffConnectionString(), "staffConnection {\npageInfo {\nlastPage\n}\n}");
    }
}