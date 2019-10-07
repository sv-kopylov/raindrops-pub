-- create tables
                CREATE CACHED TABLE dataset  
                        ( 
                        id BIGINT PRIMARY KEY, 
                        dropsize DOUBLE, 
                        distance INTEGER, 
                        drop_falling_speed INTEGER, 
                        human_speed INTEGER, 
                        human_height INTEGER, 
                        human_width INTEGER, 
                        human_depth INTEGER, 
                        space_width INTEGER, 
                        space_height INTEGER, 
                        space_lenght INTEGER, 
                        rain_intensyty DOUBLE, 
                        probability_drop_in_cell DOUBLE, 
                        drops_in_layer DOUBLE 
                        );

                CREATE CACHED TABLE results  
                        (
                        id BIGINT IDENTITY,
                        iteration BIGINT,
                        dataset_id BIGINT, 
                        cur_distance INTEGER,
                        top_drops BIGINT,
                        front_drops BIGINT,
                        FOREIGN KEY(dataset_id) REFERENCES dataset(id) 
                        );

truncate table results;
truncate table dataset;

select * from dataset;

select iteration, cur_distance, top_drops, front_drops from results
where dataset_id = 1570203375075
order by iteration;

select iteration, cur_distance, top_drops, front_drops from results
where dataset_id = 1570203375076
order by iteration;

select count(*) from results
where dataset_id = 1569865079300;