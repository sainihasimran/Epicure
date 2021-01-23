using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using EpicureService;

namespace EpicureService.Controllers
{
    public class StepsController : ApiController
    {
        private EpicureDbEntities db = new EpicureDbEntities();

        // GET: api/Steps
        public IQueryable<Step> GetSteps()
        {
            return db.Steps;
        }

        // GET: api/Steps/5
        [ResponseType(typeof(Step))]
        public IHttpActionResult GetStep(int id)
        {
            Step step = db.Steps.Find(id);
            if (step == null)
            {
                return NotFound();
            }

            return Ok(step);
        }

        // PUT: api/Steps/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutStep(int id, Step step)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != step.StepNumber)
            {
                return BadRequest();
            }

            db.Entry(step).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!StepExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Steps
        [ResponseType(typeof(Step))]
        public IHttpActionResult PostStep(Step step)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Steps.Add(step);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (StepExists(step.StepNumber))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = step.StepNumber }, step);
        }

        // DELETE: api/Steps/5
        [ResponseType(typeof(Step))]
        public IHttpActionResult DeleteStep(int id)
        {
            Step step = db.Steps.Find(id);
            if (step == null)
            {
                return NotFound();
            }

            db.Steps.Remove(step);
            db.SaveChanges();

            return Ok(step);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool StepExists(int id)
        {
            return db.Steps.Count(e => e.StepNumber == id) > 0;
        }
    }
}