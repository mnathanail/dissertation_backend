package com.dissertation.backend.node;

import lombok.Getter;
import lombok.Setter;

//@RelationshipEntity
@Getter
@Setter
public class RecruiterManagesRelationship {

  //  @StartNode
    RecruiterNode recruiterNode;
  //  @EndNode
    JobNode jobNode;

    public RecruiterManagesRelationship(RecruiterNode recruiterNode, JobNode jobNode){
        this.recruiterNode = recruiterNode;
        this.jobNode = jobNode;

        this.recruiterNode.getJobNodeAdvertisements().add(this.jobNode);
    }
}
